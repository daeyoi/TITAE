import pymysql

def save(Table_name, Bank_name, product , user_info):

    #dbconnection
    conn = pymysql.connect(host=user_info.get_host(), user = user_info.get_user(), passwd = user_info.get_passwd(), db= user_info.get_dbname())

    curs = conn.cursor()
    print("저장")
    if "deposit" in Table_name :
        sql = "insert into %s(bankname, productname, rate, target ,calmethod,description) value(%s,%s,%s,%s,%s,%s)" % \
        (Table_name, "'"+ Bank_name+ "'", "'"+product.get_productname()+"'","'"+product.get_rate()+"'","'"+product.get_target()+"'","'"+product.get_calmethod()+"'","'"+product.get_description()+"'")
    else :
        sql = "insert into %s(bankname, productname, rate, target, reservingmethod ,calmethod,description) value(%s,%s,%s,%s,%s,%s,%s)" % \
        (Table_name, "'"+ Bank_name+ "'", "'"+product.get_productname()+"'","'"+product.get_rate()+"'","'"+product.get_target()+"'","'"+product.get_reservingmethod()+"'","'"+product.get_calmethod()+"'","'"+product.get_description()+"'")
        
    try:
        curs.execute(sql)
        conn.commit()

    except Exception as e:
        print(str(e))
        conn.rollback()
    conn.close()
