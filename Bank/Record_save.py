import pymysql

def save(Table_name, Bank_name, product , user_info):

    #dbconnection
    conn = pymysql.connect(host=user_info.get_host(), user = user_info.get_user(), passwd = user_info.get_passwd(), db= user_info.get_dbname())

    curs = conn.cursor()

    sql = "insert into %s(bankname, productname, rate , description) value(%s,%s,%s,%s)" % \
         (Table_name, "'"+ Bank_name+ "'", "'"+product.get_productname()+"'", "'"+(product.get_rate())[:-1]+"'", "'"+product.get_description()+"'")
    try:
        curs.execute(sql)
        conn.commit()

    except Exception as e:
        print(str(e))
        conn.rollback()
    conn.close()
