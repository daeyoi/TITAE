#필수 패키지
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
import pymysql

import Record_delete
import Record_save
import Product

table_deposit = "deposit"
table_savings = "savings"

def get_info (user_info) :


    options = Options()
    #밑에 2줄 주석 처리시 창보임
    #options.add_argument('headless')
    #options.add_argument('--disable-gpu')
    
    #driver = webdriver.PhantomJS('F:/phantomjs-2.1.1-windows/bin/phantomjs.exe') # PhantomJS사용  창없는 모드
    driver = webdriver.Chrome('C:/Users/rlaal/AppData/Local/Programs/Python/chromedriver_win32/chromedriver',options=options) #창있는 모드 
    driver.get("https://www.bnksb.com/sub.do?code=02_prod01") #페이지이동
    
#-----------------------BNK 예금 ---------------------
    
    bankname="BNK저축은행"
    Record_delete.delete(table_deposit,bankname,user_info) #해당 은행 데이터 초기화
    product = Product.Product(bankname,None,None,None,None,None,None) #초기화
    product.set_bankname(bankname)

    product.set_productname("정기예금")
    product.set_rate("2.6")
    product.set_description("여윳돈으로 고수익을 보장받는 BNK저축은행의 대표적인 예금상품.\
    여유자금을 일정기간 예치하고 만기시에 원금과 이자를 한꺼 번에 받는 상품으로 \
    적은 여윳돈으로 높은 수익을 보장받을 수 있습니다")
    product.set_target("제한없음")
    product.set_calmethod("복리")
    Record_save.save(table_deposit, bankname, product, user_info)

    driver.get("https://www.bnksb.com/sub.do?code=02_prod0102") #페이지이동
    product.set_productname("정기예금")
    product.set_rate("2.0")
    product.set_description("여윳돈으로 고수익을 보장받는 BNK저축은행의 대표적인 예금상품.\
    여유자금을 일정기간 예치하고 다달이 이자를 받는 상품으로 적은 여윳돈으로 높은 수익을보장받을 수 있습니다.")
    product.set_target("제한없음")
    product.set_calmethod("단리")
    Record_save.save(table_deposit, bankname, product, user_info)


# -------------------------- BNK 적금 --------------------------
    

    driver.get("https://www.bnksb.com/sub.do?code=02_prod0202") #페이지이동
    product.set_productname("정기적금")
    product.set_rate("2.6")
    product.set_description("일정한 기간동안 가입한도를 정하고 미리정한 금액을 불입하여 목돈을 마련하는 상품입니다.")
    product.set_target("제한없음")
    product.set_calmethod("단리")
    product.set_reservingmethod("정액")
    Record_save.save(table_deposit, bankname, product, user_info)

    driver.get("https://www.bnksb.com/sub.do?code=02_prod0203") #페이지이동
    product.set_productname("정기적금")
    product.set_rate("2.05")
    product.set_description("일정한 기간을 정하고 금액 횟수에 제한없이 납입하는 상품으로 만기시 각 부금의\
실 예치기간에 따른 정기예금 복리식 이자율로 이자계산하여 원금과 함께 지급하는 상품입니다.")
    product.set_target("제한없음")
    product.set_calmethod("단리")
    product.set_reservingmethod("자유")
    Record_save.save(table_deposit, bankname, product, user_info)


    
    driver.quit()
