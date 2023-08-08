package com.amazon.pages;

import com.amazon.utilities.BrowserUtils;
import com.amazon.utilities.ConfigurationReader;
import org.bouncycastle.pqc.jcajce.provider.qtesla.QTESLAKeyFactorySpi;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    @FindBy(css = "#ap_email")
    public WebElement emailBox;

    @FindBy(css = "input#continue")
    public WebElement devamBtn;

    @FindBy(id = "ap_password")
    public WebElement passwordBox;
    @FindBy(css = "[type='submit']")
    public WebElement girisYapBtnLogin;

    public void login(){
        navigateToLoginPage();
        emailBox.sendKeys(ConfigurationReader.get("email"));
        devamBtn.click();
        //passwordBox.sendKeys(ConfigurationReader.get("password"));// alrttaki sastirla ayni isi yapar
        BrowserUtils.clearAndSendKeys(passwordBox,ConfigurationReader.get("password"));
        girisYapBtnLogin.click();


    }

}
