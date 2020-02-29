# -*- coding: UTF-8 –*-

import os
import os.path
from PyPDF2 import PdfFileReader, PdfFileWriter
import time

##########################合并同一个文件夹下所有PDF文件########################
def MergePDF():
    output = PdfFileWriter()
    outputPages = 0
    currentpath = os.path.split(os.path.realpath(__file__))[0]
    os.chdir(currentpath)
    files = os.listdir("./sources/")
    for each_file in files:
        print("adding %s" %(each_file))
        # 读取源pdf文件
        input = PdfFileReader(open("sources/" + each_file, "rb"))

        # 如果pdf文件已经加密，必须首先解密才能使用pyPdf
        if input.isEncrypted == True:
            input.decrypt("")

        # print(each_file[:-4])

        # 获得源pdf文件中页面总数
        pageCount = input.getNumPages()
        outputPages += pageCount
        print("%s has %d pages" % (each_file, pageCount))

        # 分别将page添加到输出output中
        for iPage in range(pageCount):
            output.addPage(input.getPage(iPage))

        # 添加书签
        output.addBookmark(
            title=each_file[:-3], pagenum=outputPages - pageCount)

    print("All Pages Number: " + str(outputPages))
    # 最后写pdf文件
    outputStream = open("result.pdf", "wb")
    output.write(outputStream)
    outputStream.close()
    print("finished")


if __name__ == '__main__':
    MergePDF()