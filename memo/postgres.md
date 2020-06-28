
​		

# postgres

```
 database
    schema(namespace)
        table
        view

-n schema
--schema=schema

```
## docker command
```
docker ps | grep 'NAMES\|postgres'  
docker ps --format "table {{.ID}} {{.Names}}"
docker images | grep 'REPO\|postgres'

docker logs name
docker logs -t srad-db
```

## run postgres container
```
Usage:	docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
  -d, --detach                         Run container in background and print container ID

docker run --name postgres_5434 -p 5434:5432 -d postgres:10.11
                 container name, port,  detach, image
docker run --name postgres -p 5432:5432 -e POSTGRES_USER=dev -e POSTGRES_PASSWORD=dev -d postgres

```

## attach
```
windows:
docker exec -it postgres_5434 /bin/bash 
docker exec -it postgres_5434 /bin/sh
docker exec -it postgres_5434 sh or bash

/data #
git bash
winpty docker exec -it postgres_5434 sh
/data #
```

## use psql
- psql -h host -p port -U user db
  - from windows
    - psql -h localhost -p 5434 -U postgres postgres
  - in container
    - docker exec -it postgres_5434 sh
    - psql -h localhost -p 5432 -U postgres postgres
  - localhost database
    - psql -U postgres

- database
  - list db: \l, \list
    
    - SELECT datname FROM pg_database WHERE datistemplate = false
  - create: create database bxlink;
  - curren db:  \conninfo
    ```
    データベース"bxlink"にユーザ"postgres"として、ホスト"localhost"(アドレス"::1")上のポート"5434"で接続しています。
    ```
    - SELECT current_database();
  - connect db: \c, \connect dbname
- schema and table
  - list schema: \dn (namespace)
  - create schema: create schema stockpile;
  - current schema: select current_schema();
  - set current schema: SET search_path = stockpile, testschema;
    - show search_path;
        ```
            search_path
        -----------------------
        stockpile, testschema
        (1 行)
        ```

  - ¥d : オブジェクト表示
    - ¥d[種類][S][+] パターン
      - S : システムオブジェクトも表示します。
      - + : 詳細を表示します。
      - パターン : オブジェクトの名前です。ワイルドカード (? や *) を含むことができます。使用例:
        - =# ¥d pgbench_*  -- 名前が pgbench_ から始まるテーブルを表示
        - =# ¥df *regexp*  -- 正規表現を扱う関数を表示
    - psql の代表的な ¥d コマンド
      - コマンド	覚え方	表示対象
      - ¥d	(describe)	テーブル、ビュー、シーケンス
      - ¥da	aggregate	集約関数
      - ¥db	tablespace	テーブルスペース
      - ¥dC	Cast	キャスト
      - ¥df	function	関数
      - ¥di	index	インデックス
      - ¥dn	namespace	スキーマ (名前空間)
      - ¥do	operator	演算子
      - ¥ds	sequence	シーケンス
      - ¥dt	table	テーブル
      - ¥dT	Type	型
      - ¥du	user	ロール (ユーザ, グループ)
      - ¥dv	view	ビュー

- ¥copy : 遠隔データロード
- ¥x : 列を縦に展開表示
  
- ¥x で列を縦に展開する表示と切り替えることができます。もう一度入力すると元に戻ります。
  
- pager
  
- \pset pager off
  
- シーケンス操作関数
 - seq table
   - create sequence
    ```
    -- DROP SEQUENCE stockpile.t_test_seq;
    CREATE SEQUENCE stockpile.t_test_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
    ```

    - function
    ```
    関数	返り値	説明
    nextval(text)	bigint	シーケンスを進め新規返り値を戻す
    currval(text)	bigint	nextval で得られた直近の返り値
    setval(text,bigint)	bigint	シーケンスの現在値を設定
    setval(text,bigint,boolean)	bigint	シーケンスの現在値と is_called フラグを設定
    ```

    - select value
    ```  
    select * from t_test_seq
    last_value	log_cnt	is_called
    1	0	false
    
    is_called: false -> currval(t_test_seq) -> error
    bxlink=# select currval('t_test_seq');
    ERROR:  currval of sequence "t_test_seq" is not yet defined in this session

    bxlink=# select * from t_test_seq;
    last_value | log_cnt | is_called
    ------------+---------+-----------
            1 |       0 | f

    is_called: false -> nextval('seqName') -> last_value
    bxlink=# select nextval('t_test_seq');
    nextval
    ---------
        1   
     bxlink=# select nextval('t_test_seq');
    nextval
    ---------
        2
 
    bxlink=# select * from t_test_seq;
    last_value | log_cnt | is_called
    ------------+---------+-----------
            2 |      32 | t
    ```
    - setval
      - select setval('t_test_seq', (select last_value from t_test_seq))
      - update -> error


- pg_dump
  - pg_dump for **schema**
    - pg_dump -p 5432 -U postgres -n stockpile bxlink > bxlink_stockpile_data.bak
    - pg_dump -h stockpile-database-1.cluster-czsehjnirg8d.ap-northeast-1.rds.amazonaws.com -p 5432 -U postgres -n stockpile bxlink > bxlink_stockpile_aws_data.bak
  - pg_dump -h hostname -p port -U username database名 > fileName
    - localhost: pg_dump -p port -U username database名 > fileName
  - restore: psql -h hostname -p port -U username database名 < fileName
    - localhost: psql -p port -U username database名 < fileName


- hint
```
ヒント: \copyright とタイプすると、配布条件を表示します。
       \h とタイプすると、SQL コマンドのヘルプを表示します。
       \? とタイプすると、psql コマンドのヘルプを表示します。
       \g と打つかセミコロンで閉じると、問い合わせを実行します。
       \q で終了します。
```

### create volume
- $ docker volume create postgres-tmp
```
docker run --rm -d  -p 15432:5432   -v postgres-tmp:/var/lib/postgresql/data postgres
```

### log

```
command: postgres -c log_destination=csvlog -c log_statement=all -c log_connections=on -c log_disconnections=on
```

### show plan

```
# explain select count(*) from m_menu;
                         QUERY PLAN
-------------------------------------------------------------
 Aggregate  (cost=2.75..2.76 rows=1 width=8)
   ->  Seq Scan on m_menu  (cost=0.00..2.60 rows=60 width=0)
(2 行)
```






​		