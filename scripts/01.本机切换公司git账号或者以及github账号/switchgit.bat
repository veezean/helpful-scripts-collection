@echo off
cls

::公司的git账号邮箱信息
set COMPANY_USERNAME="XXXX"
set COMPANY_EMAIL="XXXX@XXXXX.com"

::Github的账号邮箱信息
set GITHUB_USERNAME="XXXXX"
set GITHUB_EMAIL="XXXXX@XXXXX.com"



echo.
echo.
echo *****************************************************************
echo 请输入编号选择要切换到的git账号：
echo.
echo 1  公司git账号
echo 2  github账号
echo.

set /p id=请选择：

echo.
echo.

if "%id%" == "1" (
    echo 开始切换到公司git账号...
    git config --global user.name %COMPANY_USERNAME%
    git config --global user.email %COMPANY_EMAIL%
    goto PrintAccount    
) else if "%id%" == "2" (
    echo 开始切换到github账号...
    git config --global user.name %GITHUB_USERNAME%
    git config --global user.email %GITHUB_EMAIL%
    goto PrintAccount
) else (
    goto Error    
)


:PrintAccount
echo.
echo.
echo 切换完成!!!
echo.
echo.
echo 当前使用的git账号信息如下：
git config user.name
git config user.email

goto End


:Error
echo 输入错误！！！请确认后重新执行命令！！！
goto End

:End
echo.
echo *****************************************************************
echo.

