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
  "estimated_received_amount" decimal,
  "fee" decimal,
  "rate" decimal,
  "estimatedrate" decimal,
  "operation_requested" text,
  "set_fee" text,
  "operation_approved" text,
  "operation_executed" text,
  "endtoend_id" text,
  "apimguid" text,
  "acctsvcrref" text,
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
) VALUES ('US111111', 'CITIBANK', 'MIAMI'),
  ('MX222222', 'MEXICAN BANK', 'CIUDAD DE MEXICO'),
  ('PR333333', 'PUERTO RICO BANK', 'SAN JUAN');
 
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
) VALUES ('0X39316977859458E9DDF5B2AE74196A059927BF56','111-1111-111111','USD',0,0,'US111111', '123456'),
  ('0X173CF75F0905338597FCD38F5CE13E6840B230E9','222-2222-222222','MXN',0,0,'MX222222', '789012'),
  ('0XAAACF75F0905338597FCD38F5CE13E6840B230EA','333-3333-333333','PPR',0,0,'PR333333', '345678'),
  ('0XBCEDA2BA9AF65C18C7992849C312D1DB77CF008E','123-45678-9012345','USD',0,0,'US111111','901234'),
  ('0X0000000000000000000000000000000000000000','123-45678-9012345','USD',0,0,'US111111','123456');


INSERT INTO public.movements
(
	"id",
  "datetime",
  "sender",
  "receiver",
  "amount",
  "detail",
  "received_amount",
  "estimated_received_amount",
  "fee",
  "rate",
  "estimatedrate",
  "operation_requested",
  "set_fee",
  "operation_approved",
  "operation_executed",
  "endtoend_id",
  "apimguid",
  "acctsvcrref",
  "status") VALUES('654565','2020-05-10 09:00:00','0XBCEDA2BA9AF65C18C7992849C312D1DB77CF008E','0XAAACF75F0905338597FCD38F5CE13E6840B230EA',500,'Conversion to Pesos',490,490,0.10,0.5,0.5,'0xb5c8bd9430b6cc87a0e2fe110ece6bf527fa4f170a4bc8cd032f768fc5219838','0xff38ab554666098ccda9c521fb8eb400198431012a10bf95c0b005d17ec5d838','0xe125bc611e84edc1a647f8e3bea4198078559e534b6d39af8a82fb78d24fd9b8','0xfeff518a00baf4d48f842dcc223dc70ed294b869b08a3d34d1bc07aaaf211d6a','1230BI0R8Z4L800','na-apimgwgtdu03~60505978-6ccd-4a40-8ddb-1580c310603a','KK6DDO29JIIL',1),
 ('978798','2020-05-10 10:00:00','0X173CF75F0905338597FCD38F5CE13E6840B230E9','0XBCEDA2BA9AF65C18C7992849C312D1DB77CF008E',100,'conversion to dollars',96,96,0.70,1,1,'0xb5c8bd9430b6cc87a0e2fe110ece6bf527fa4f170a4bc8cd032f768fc5219838','0xff38ab554666098ccda9c521fb8eb400198431012a10bf95c0b005d17ec5d838','0xe125bc611e84edc1a647f8e3bea4198078559e534b6d39af8a82fb78d24fd9b8','0xfeff518a00baf4d48f842dcc223dc70ed294b869b08a3d34d1bc07aaaf211d6a','END23456','na-apimgwgtdu03~60505978-6ccd-4a40-8ddb-1580c310603b','KK6DDO29JIIL',4), 
 ('12656547','2020-05-10 11:00:00','0XBCEDA2BA9AF65C18C7992849C312D1DB77CF008E','0X39316977859458E9DDF5B2AE74196A059927BF56',100,'send to citi',100,100,0.66,1.5,1.5,'0xb5c8bd9430b6cc87a0e2fe110ece6bf527fa4f170a4bc8cd032f768fc5219838','0xff38ab554666098ccda9c521fb8eb400198431012a10bf95c0b005d17ec5d838','0xe125bc611e84edc1a647f8e3bea4198078559e534b6d39af8a82fb78d24fd9b8','0xfeff518a00baf4d48f842dcc223dc70ed294b869b08a3d34d1bc07aaaf211d6a','END876786','na-apimgwgtdu03~60505978-6ccd-4a40-8ddb-1580c310603c','KK6DDO29JIIL',1);  
 

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
	  CASE WHEN movements.status IN (0,1,2,5) THEN (movements.amount/movements.estimatedrate) ELSE movements.received_amount END AS "converted_amount",
	  CASE WHEN movements.status IN (0,1,2,5) THEN movements.estimatedrate ELSE movements.rate END AS "rate_applied",
	  CASE WHEN movements.status IN (0,1,2,5) THEN (movements.amount/movements.estimatedrate) ELSE (movements.received_amount/movements.rate) END AS "recipient_will_get",
	
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
  movements.operation_executed,
  movements.endtoend_id,
  movements.apimguid,
  movements.acctsvcrref,
  CASE WHEN movements.status = 0 THEN 'REQUESTED' WHEN movements.status = 1 THEN 'APPROVED' WHEN movements.status = 2 THEN 'IN PROGRESS' WHEN movements.status = 3 THEN 'FEE-RATE SETED' WHEN movements.status = 4 THEN 'COMPLETED' WHEN movements.status = 5 THEN 'CANCELLED' ELSE 'FAILED' END AS status
	
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

	WHERE UPPER(movements.sender) = UPPER(sender.dlt_address) and UPPER(movements.receiver) = UPPER(receiver.dlt_address);