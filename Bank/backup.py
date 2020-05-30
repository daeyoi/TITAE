from selenium import webdriver
from selenium.webdriver.chrome.options import Options

from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

import time

import pymysql #mysql디비 연결


table_savings = "savings"
table_deposit = "deposit"

#------------------------------db에 예금 데이터 저장하는 함수--------------------
def save_record(Table_name,Bank_name, Product_name, Rate, Description):

    #dbconnection
    conn = pymysql.connect(host="localhost", user = "root", passwd = "1234", db= "titae")

    curs = conn.cursor()
    rt = Rate[:-1]
   
    sql = "insert into %s(bankname, productname, rate , description) value(%s,%s,%s,%s)" % \
         (Table_name, "'"+ Bank_name+ "'", "'"+Product_name+"'", "'"+rt+"'", "'"+Description+"'")
    try:
        curs.execute(sql)
        conn.commit()

    except Exception as e:
        print(str(e))
        conn.rollback()
    conn.close()


#--------------------------------은행 데이터 db 초기화------------------
def clear_record(Table_name,bankname) :
    #dbconnection
    conn = pymysql.connect(host="localhost", user = "root", passwd = "1234", db= "titae")


    curs = conn.cursor()

    sql = "delete from %s where bankname = %s;" % (Table_name, "'"+bankname+"'")
  
    try:
        curs.execute(sql)
        conn.commit()

    except Exception as e:
        print(str(e))
        conn.rollback()
    conn.close()



#------------------------------------국민은행 예금 리스트--------------------------------------------------------
#driver = webdriver.PhantomJS('F:/phantomjs-2.1.1-windows/bin/phantomjs.exe') # PhantomJS사용
driver = webdriver.Chrome('C:/Users/rlaal/AppData/Local/Programs/Python/chromedriver_win32/chromedriver')

driver.get("https://obank.kbstar.com/quics?page=C016613&cc=b061496:b061496#CP") #페이지이동
cnt = int(driver.find_element_by_xpath("//h2[@class='tit_dep3 total_num']/strong").text) #페이지당 상품 수
productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
rate = driver.find_elements_by_xpath("//div[@class='info-data2']/strong")
description = driver.find_elements_by_xpath("//span[@class='msg']")

bankname="국민은행"
clear_record(table_deposit,bankname) #해당 은행 데이터 초기화

for i in range(0,cnt-2) :
    save_record(table_deposit,bankname, productname[i%10].text ,rate[i%10].text, description[i%10].text)
    
    if i%10 ==9 :
        if(cnt >=10) :
            driver.execute_script("goPage(arguments[0])",page) #페이지 이동
           # element = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, "area1")))
            driver.implicitly_wait(3)     
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
clear_record(table_savings,bankname) #해당 은행 데이터 초기화
page = int(2)

for i in range(0,cnt) :

    save_record(table_savings, bankname, productname[i%10].text,rate[i%10].text,description[i%10].text) #상품 db에 저장
    
    if i%10 == 9 :
        if(cnt >=10) :
            driver.execute_script("goPage(arguments[0])",page) 
            cnt = cnt -10
            page = page + 1
            time.sleep(1)
            productname = driver.find_elements_by_xpath("//div[@class='area1']/a")
            rate = driver.find_elements_by_xpath("//div[@class='info-data2']/strong")
            description = driver.find_elements_by_xpath("//span[@class='msg']")



#------------------------하나은행 예금 --------------------------------

driver.get("https://www.kebhana.com/cont/mall/mall08/mall0805/index.jsp?_menuNo=62608") #페이지이동
cnt = int(driver.find_element_by_xpath("//span[@class='count']").text) #페이지당 상품 수
productname = driver.find_elements_by_xpath("//span[@class='product-tit']/em")
rate = driver.find_elements_by_xpath("//span[@class='desc-detail']/strong")
description = driver.find_elements_by_xpath("//span[@class='tit-desc']/a")

bankname="KEB하나은행"
clear_record(table_deposit, bankname) #해당 은행 데이터 초기화

for i in range(0,cnt) :
    if "중단" in productname[i].text :
        continue
    save_record(table_deposit, bankname, productname[i].text ,rate[i].text, description[i].text)



# --------------------------하나은행 적금 --------------------------
driver.execute_script("doTab('spb_2812')")
cnt = int(driver.find_element_by_xpath("//span[@class='count']").text) #페이지당 상품 수
productname = driver.find_elements_by_xpath("//span[@class='product-tit']/em")
rate = driver.find_elements_by_xpath("//span[@class='desc-detail']/strong")
description = driver.find_elements_by_xpath("//span[@class='tit-desc']/a")

clear_record(table_savings, bankname) #해당 은행 데이터 초기화

for i in range(0,cnt) :
    if "중단" in productname[i].text :
        continue
    driver.find_element_by_partial_link_text(productname[i].text).click()
    driver.back()
    save_record(table_savings, bankname, productname[i].text ,rate[i].text, description[i].text)



#---------


            

driver.quit()
