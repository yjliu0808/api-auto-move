package com.cases;

import com.parameters.LoginParams;
import com.pojo.CaseInfo;
import com.utils.Constants;
import com.utils.ExcelUtils;
import com.utils.HttpUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;


public class LoginCase extends BaseCase {

    private Logger logger = Logger.getLogger(LoginCase.class);

    @Test(dataProvider = "datas",description = "登录用例")
    @Description("Some detailed test description")
    @Step("url: {caseInfo.url} ")
    @Severity(SeverityLevel.CRITICAL)
    public void test(CaseInfo caseInfo) {
        //  1、参数化替换
        LoginParams.paramsReplace();
        paramsReplace(caseInfo);

        //	2、数据库前置查询结果(数据断言必须在接口执行前后都查询)
        //	3、调用接口
        Map<String, Object> headers = HttpUtils.getDefaultHeaders();
        String body = HttpUtils.call(caseInfo, headers);
        //把token存储到UserData
        response2UserData(body, "$.data.token_info.token", "${token}");
        //把member_id存储到UserData
        response2UserData(body, "$.data.id", "${member_id}");
        //	4、断言响应结果
        boolean responseAssertResult = responseAssert(caseInfo, body);
        //	5、添加接口响应回写内容
        //回写=excel修改操作
        addWriteBackData(caseInfo.getCaseId(),Constants.RESPONSE_CELLNUM,startSheetIndex,body);
        //	6、数据库后置查询结果
        //	7、据库断言
        //	8、添加断言回写内容
        String assertResult = responseAssertResult ? "passed" : "failed";
        addWriteBackData(caseInfo.getCaseId(),Constants.ASSERT_CELLNUM,startSheetIndex,assertResult);
        //	9、添加日志
        //	10、报表断言
        Assert.assertEquals(assertResult,"passed");


    }


    @DataProvider
    public Object[] datas() throws Exception {
        //1、从excel读取用例信息
        return ExcelUtils.read(startSheetIndex,sheetNum);
    }
    
}
