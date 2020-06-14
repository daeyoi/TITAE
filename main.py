import User

import KB_bank
import KEBHANA_bank
import BNK_bank
import NH_savebank
host = "localhost"
user = "root"
passwd = "1234"
dbname = "titae"

user_info = User.User(host,user,passwd,dbname)

#KB_bank.get_info(user_info)
#KEBHANA_bank.get_info(user_info)
#BNK_bank.get_info(user_info)
NH_savebank.get_info(user_info)
