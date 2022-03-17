import os.path
rootdir = 'G:/源码/MinestomTest/src/main/resources/biomes'
list = os.listdir(rootdir)
result = ''
for i in range(0,len(list)):
       result+= ('"'+os.path.basename(list[i])+'", ')
print(result)
