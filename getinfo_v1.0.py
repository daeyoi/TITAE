from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import time

import pymysql #mysql디비 연결

#db에 데이터 저장하는 함수
def save_record(Bank_name, Product_name, Rate, Description):

    #dbconnection
    conn = pymysql.connect(host="localhost", user = "root", passwd = "1234", db= "titae")


    curs = conn.cursor()
    rt = Rate[:-1]
   
    sql = "insert into deposit(bankname, productname, rate , description) \
             value(%s,%s,%s,%s)" % \
             ("'" + Bank_name+ "'", "'"+Product_name+"'", "'"+rt+"'", "'"+Description+"'")
    try:
        curs.execute(sql)
        conn.commit()

    except Exception as e:
        print(str(e))
        conn.rollback()
    conn.close()


driver = webdriver.PhantomJS('F:/phantomjs-2.1.1-windows/bin/phantomjs.exe') # PhantomJS사용
driver.get("https://obank.kbstar.com/quics?page=C016613&cc=b061496:b061496#CP")

#은행 데이터 초기화
def clear_record(bankname) :
    #dbconnection
    conn = pymysql.connect(host="localhost", user = "root", passwd = "1234", db= "titae")


    curs = conn.cursor()
   
    sql = "delete from deposit where bankname = %s" % ("'" + bankname+ "'")
    
    try:
        curs.execute(sql)
        conn.commit()

    except Exception as e:
        print(str(e))
        conn.rollback()
    conn.close()


#------------------------------------국민은행 예금 리스트--------------------------------------------------------
cnt = int(driver.find_element_by_xpath("//h2[@class='tit_dep3 total_num']/strong").text) #페이지당 상품 수
productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
rate = driver.find_elements_by_xpath("//div[@class='info-data2']/strong")
description = driver.find_elements_by_xpath("//span[@class='msg']")

bankname="국민은행"
clear_record(bankname) #해당 은행 데이터 초기화

for i in range(0,cnt-2) :

    save_record(bankname, productname[i%10].text ,rate[i%10].text, description[i%10].text)
    
    if i%10 ==9 :
        if(cnt >=10) :
            driver.execute_script("goPage(arguments[0])",page) #페이지 이동
            cnt = cnt -10
            print("---------------")
            page = page + 1
            time.sleep(1)
            productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
            rate = driver.find_elements_by_xpath("//div[@class='info-data2']/strong")
            description = driver.find_elements_by_xpath("//span[@class='msg']")
cnt = 0

#--------------------------------------------------국민은행 적금리스트로 이동-------------------------------------------------
driver.execute_script("fnMenuClick('00027')")
time.sleep(1)
cnt = int(driver.find_element_by_xpath("//h2[@class='tit_dep3 total_num']/strong").text) #페이지당 상품 수

#필요항목 추출
productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
rate = driver.find_elements_by_xpath("//div[@class='info-data2']/strong")
description = driver.find_elements_by_xpath("//span[@class='msg']")

page = int(2)
k = 0;

for i in range(0,cnt) :

    save_record(bankname, productname[i%10].text,rate[i%10].text,description[i%10].text) #상품 db에 저장
    
    if i%10 == 9 :
        if(cnt >=10) :
            driver.execute_script("goPage(arguments[0])",page)
            cnt = cnt -10
            print("---------------")
            page = page + 1
            time.sleep(1)
            productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
            rate = driver.find_elements_by_xpath("//div[@class='info-data2']/strong")
            description = driver.find_elements_by_xpath("//span[@class='msg']")

driver.quit()
