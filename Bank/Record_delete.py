import pymysql

def delete(Table_name,bankname,user_info) :
    #dbconnection
    conn = pymysql.connect(host=user_info.get_host(), user = user_info.get_user(), passwd = user_info.get_passwd(), db= user_info.get_dbname())
    curs = conn.cursor()

    sql = "delete from %s where bankname = %s;" % (Table_name, "'"+bankname+"'")
  
    try:
        curs.execute(sql)
        conn.commit()

    except Exception as e:
        print(str(e))
        conn.rollback()
    conn.close()
