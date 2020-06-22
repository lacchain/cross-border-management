CREATE DATABASE crossborder;

\c crossborder

DROP TABLE IF EXISTS public.users;
CREATE TABLE IF NOT EXISTS public.users
(
  "id" text NOT NULL,
  "fullname" text NOT NULL,
  "email" text NOT NULL,
  "password" text NOT NULL,
  "company" text NOT NULL,
  "role" text NOT NULL,
  PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS public.banks;
CREATE TABLE IF NOT EXISTS public.banks
(
  "tax_id" text NOT NULL,
  "name" text NOT NULL,
  "city" text NOT NULL,
  PRIMARY KEY ("tax_id")
);

DROP TABLE IF EXISTS public.accounts;
CREATE TABLE IF NOT EXISTS public.accounts
(
  "dlt_address" text NOT NULL,
  "bank_account" text NOT NULL,
  "currency" text,
  "balance" decimal,
  "status" int,
  "bank_id" text NOT NULL,
  "user_id" text NOT NULL,
  PRIMARY KEY ("dlt_address"),
  FOREIGN KEY ("bank_id") REFERENCES public.banks ("tax_id"),
  FOREIGN KEY ("user_id") REFERENCES public.users ("id")
);

DROP TABLE IF EXISTS public.movements;
CREATE TABLE IF NOT EXISTS public.movements
(
	"id" text NOT NULL,
  "datetime" timestamp,
  "sender" text,
  "receiver" text,
  "amount" decimal,
  "detail" text,
  "received_amount" decimal,
  "fee" decimal,
  "rate" decimal,
  "operation_requested" text,
  "set_fee" text,
  "operation_approved" text,
  "status" int,
  PRIMARY KEY ("id"),
  FOREIGN KEY ("sender") REFERENCES public.accounts ("dlt_address"),
  FOREIGN KEY ("receiver") REFERENCES public.accounts ("dlt_address")	
);

INSERT INTO public.banks
(
  "tax_id",
  "name",
  "city"
) VALUES ('US111111', 'CITIBANK', 'Miami'),
  ('MX222222', 'MEXICAN BANK', 'Ciudad de Mexico'),
  ('PR333333', 'PUERTO RICO BANK', 'San Juan');
 
INSERT INTO public.users
(
  "id",
  "fullname",
  "email",
  "password", 
  "company",
  "role"
) VALUES ('123456', 'Juan Perez', 'juancito@citi.com','111111','Citibank','ROLE_CITI'),
  ('789012', 'Jose Ruiz', 'jose@mexico.com','222222','Mexican Bank','ROLE_USER'),
  ('345678', 'Julio Garcia', 'julio@puerto.com','333333','Puerto Rico Bank','ROLE_USER'),
  ('901234', 'Mario Cardenas', 'mario@iadb.org','444444','BiD','ROLE_USER');
  
INSERT INTO public.accounts
(
  "dlt_address",
  "bank_account",
  "currency",
  "balance",
  "status",	
  "bank_id",
  "user_id"
) VALUES ('0x39316977859458E9dDf5B2Ae74196a059927bf56','111-1111-111111','USD',0,0,'US111111', '123456'),
  ('0x173CF75f0905338597fcd38F5cE13E6840b230e9','222-2222-222222','MXN',0,0,'MX222222', '789012'),
  ('0xAAACF75f0905338597fcd38F5cE13E6840b230eA','333-3333-333333','PPR',0,0,'PR333333', '345678'),
  ('0xbcEda2Ba9aF65c18C7992849C312d1Db77cF008E','123-45678-9012345','USD',0,0,'US111111','901234'),
  ('0x0000000000000000000000000000000000000000','123-45678-9012345','USD',0,0,'US111111','123456');


INSERT INTO public.movements
(
	"id",
  "datetime",
  "sender",
  "receiver",
  "amount",
  "detail",
  "received_amount",
  "fee",
  "rate",
  "operation_requested",
  "set_fee",
  "operation_approved",
  "status"
) VALUES('654565','2020-05-10 09:00:00','0xbcEda2Ba9aF65c18C7992849C312d1Db77cF008E','0xAAACF75f0905338597fcd38F5cE13E6840b230eA',500,'Conversion to Pesos',490,0.10,0.5,'0xb5c8bd9430b6cc87a0e2fe110ece6bf527fa4f170a4bc8cd032f768fc5219838','0xff38ab554666098ccda9c521fb8eb400198431012a10bf95c0b005d17ec5d838','0xe125bc611e84edc1a647f8e3bea4198078559e534b6d39af8a82fb78d24fd9b8',1),
 ('978798','2020-05-10 10:00:00','0x173CF75f0905338597fcd38F5cE13E6840b230e9','0xbcEda2Ba9aF65c18C7992849C312d1Db77cF008E',100,'conversion to dollars',96,0.70,1,'0xb5c8bd9430b6cc87a0e2fe110ece6bf527fa4f170a4bc8cd032f768fc5219838','0xff38ab554666098ccda9c521fb8eb400198431012a10bf95c0b005d17ec5d838','0xe125bc611e84edc1a647f8e3bea4198078559e534b6d39af8a82fb78d24fd9b8',2), 
 ('12656547','2020-05-10 11:00:00','0xbcEda2Ba9aF65c18C7992849C312d1Db77cF008E','0x39316977859458E9dDf5B2Ae74196a059927bf56',100,'send to citi',100,0.66,1.5,'0xb5c8bd9430b6cc87a0e2fe110ece6bf527fa4f170a4bc8cd032f768fc5219838','0xff38ab554666098ccda9c521fb8eb400198431012a10bf95c0b005d17ec5d838','0xe125bc611e84edc1a647f8e3bea4198078559e534b6d39af8a82fb78d24fd9b8',1);  
 

CREATE VIEW users_view AS
SELECT accounts.dlt_address, users.company, users.fullname, users.email, banks.name, banks.tax_id, 
       banks.city, accounts.bank_account, accounts.currency, accounts.balance, 
       CASE WHEN accounts.status = 0 THEN 'Requested' ELSE 'Active' END AS status
FROM users
INNER JOIN accounts ON accounts.user_id = users.id
INNER JOIN banks ON banks.tax_id = accounts.bank_id;


CREATE VIEW movements_view AS 
SELECT DISTINCT movements.id AS "id",
    movements.datetime AS "datetime",
    movements.amount AS "sent_amount",
	movements.fee AS "fee_applied",
	movements.received_amount AS "converted_amount",
	movements.rate AS "rate_applied",
	(movements.received_amount*movements.rate) AS "recipient_will_get",
	
    sender.fullname AS "sender_name",
	sender.name AS "sender_bank",
	sender.bank_account AS "sender_bank_account",
	sender.dlt_address AS "sender_dlt_address",
	sender.currency AS "sender_currency",
	
	receiver.fullname AS "receiver_name",
	receiver.name AS "receiver_bank",
	receiver.bank_account AS "receiver_bank_account",
	receiver.dlt_address AS "receiver_dlt_address",
    receiver.currency AS "receiver_currency",

  movements.operation_requested,
  movements.set_fee,
  movements.operation_approved,
  CASE WHEN movements.status = 0 THEN 'REQUESTED' WHEN movements.status = 1 THEN 'IN PROGRESS' ELSE 'COMPLETED' END AS status
	
	FROM movements, users, banks, (
    								SELECT users.fullname, banks.name, accounts.bank_account, accounts.dlt_address, accounts.currency
    								FROM users
    								INNER JOIN accounts ON accounts.user_id = users.id
									INNER JOIN banks ON banks.tax_id = accounts.bank_id
    							) AS sender,
								(
    								SELECT users.fullname, banks.name, accounts.bank_account, accounts.dlt_address, accounts.currency
    								FROM users
    								INNER JOIN accounts ON accounts.user_id = users.id
									INNER JOIN banks ON banks.tax_id = accounts.bank_id
    							) AS receiver

	WHERE movements.sender = sender.dlt_address and movements.receiver = receiver.dlt_address;