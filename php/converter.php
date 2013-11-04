<?php

echo("Converting GDB database to MySQL");

$ibase_connect = ibase_connect("localhost:C:\CADASTROS.GDB", "sysdba", "masterkey", 'ISO8859_1', '100', '1') 
	or die("Cannot connect to CADASTROS.GDB");

$mysql_connect = ibase_connect("localhost", "root", "password") 
	or die("Cannot connect to MySQL");

if (!mysql_select_db("identical")) {
	die("Cannot select MySQL db.");
}

if (!mysql_query("TRUNCATE TABLE customer")) {
	die("Cannot truncate table customer.");
}
	
$insert_query = "INSERT INTO customer ( "
	."name,"
	."cpf_cnpj,"
	."address,"
	."birth_date,"
	."business_phone,"
	."cell_phone,"
	."state_abbreviation,"
	."city,"
	."corporate_name,"
	."email,"
	."fax,"
	."legal_person,"
	."observation,"
	."post,"
	."problems,"
	."recort_date,"
	."residential_phone,"
	."district) VALUES ";

$ibase_select_query = ibase_query("SELECT * FROM TCLIENTE ORDER BY CLICOD ASC");

while($line = ibase_fetch_assoc($ibase_select_query)) {
	
	$local_ibase_select_query = $ibase_select_query;
	$local_ibase_select_query .= "("
		."\'".$line["CLI_NOME"]."\'"
		."\'".$line["CLI_CPFCNPJ"]."\'"
		."\'".$line["CLI_ENDERECO"]."\'"
		."\'".$line["CLI_NASCTO"]."\'"
		."\'".$line["CLI_FONECOM1"]."\'"
		."\'".$line["CLI_CELULAR"]."\'"
		."\'".$line["CID_UF"]."\'"
		."\'".$line["CLI_NOME"]."\'"
		.""
		.""
		.""
		.""
	
}
