package com.tlsdbswl.businessinfo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.tlsdbswl.businessinfo.vo.BusinessInfoListVO;
import com.tlsdbswl.businessinfo.vo.BusinessInfoVO;


@Controller
public class ApiController {

	private final static Logger logger = LoggerFactory.getLogger(ApiController.class);
	


	public static String nvl(String str, String defaultStr) {
        return str == null ? defaultStr : str ;
	}
	
	@RequestMapping(value = "/main.do")
	public ModelAndView main(ModelAndView mav) {
		
		mav.setViewName("/main");
		return mav;
	}
	
	@RequestMapping(value = "/api/businessno-form.do")
	public String businessNoForm() {

		return "/api/businessNoForm";
	}
	
	@RequestMapping(value = "/api/business-info-form.do")
	public String businessInfo() {

		return "/api/businessInfoForm";
	}

	@ResponseBody
	@RequestMapping(value = "/api/businessno-form-end.do")
	public ModelAndView selectBusinessNoList(ModelAndView mav, @RequestParam String businessRegNo, HttpServletRequest req) throws DocumentException {
		String businessregno = req.getParameter("businessRegNo");
		logger.debug("resNo {}",businessRegNo);

		//���ܴ�����? ����ó��?
		if (businessregno == null || businessregno.equals("")) {
			throw new RuntimeException("��ȸ�� ����� ��� ��ȣ�� �Է����ּ���");
		}

		String txprDscmNo = StringUtils.replace(businessregno, "-", ""); //���ڿ����� ã�� ���ڿ��� ã�� ���湮�ڷ� �����Ͽ� ��ȯ�Ѵ�.
		if (txprDscmNo.length() != 10) {
			throw new RuntimeException("��ȸ�� ����� ��� ��ȣ�� �ùٸ��� �Է����ּ���");
		}

		String dongCode = txprDscmNo.substring(3, 5);
		logger.debug("dongCode {}", dongCode);

		String url = "https://teht.hometax.go.kr/wqAction.do?actionId=ATTABZAA001R08&screenId=UTEABAAA13&popupYn=false&realScreenId=";

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/xml; charset=UTF-8");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
		headers.put("Connection", "keep-alive");
		headers.put("Content-Length", "257");
		headers.put("Content-Type", "application/xml; charset=UTF-8");
		headers.put("Host", "teht.hometax.go.kr");
		headers.put("Origin", "https://teht.hometax.go.kr");
		headers.put("Referer", "https://teht.hometax.go.kr/websquare/websquare.html?w2xPath=/ui/ab/a/a/UTEABAAA13.xml");
		headers.put("Sec-Fetch-Mode", "cors");
		headers.put("Sec-Fetch-Site", "same-origin");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");

		String crlf = "\n";

		StringBuffer sb = new StringBuffer(); //���ڿ��� �߰��ϰų� ���� �� �� �ַ� ����ϴ� �ڷ���
		sb.append("<map id=\"ATTABZAA001R08\">" + crlf);
		sb.append(" <pubcUserNo/>" + crlf);
		sb.append(" <mobYn>N</mobYn>" + crlf);
		sb.append(" <inqrTrgtClCd>1</inqrTrgtClCd>" + crlf);
		sb.append(" <txprDscmNo>" + txprDscmNo + "</txprDscmNo>" + crlf);
		sb.append(" <dongCode>" + dongCode + "</dongCode>" + crlf);
		sb.append(" <psbSearch>Y</psbSearch>" + crlf);
		sb.append(" <map id=\"userReqInfoVO\"/>" + crlf);
		sb.append("</map>" + crlf);

		System.out.println(sb);
		
		String body = sb.toString();
		
		Map<String, String> map = new HashMap<String, String>();
		
		try { // Jsoup : �ڹٷ� ������� HTML �ļ�(Parser)
			Response res = Jsoup.connect(url).headers(headers).requestBody(body).timeout(3000).method(Method.POST).execute();
			
			logger.debug("!!!!!!!! {}", res);
			
			if(logger.isDebugEnabled()) {
				logger.debug("###########"+res.body());
			}
			
			Document document = DocumentHelper.parseText(res.body());
			String trtCntn = nvl(document.valueOf("//map/trtCntn"), "");
			
			if(logger.isDebugEnabled()) {
				logger.debug("trtCntn {}",trtCntn);
			}
			
			map.put(businessRegNo, trtCntn);
			
		} catch (IOException e) {
			logger.error("Jsoup ����", e);
		} 
		
		logger.debug("map {}",map);
		
		mav.addObject("list", map);
		mav.setViewName("api/businessNoList");
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/business-info-end.do")
	public ModelAndView selectBusinessInfoList(ModelAndView mav, HttpServletRequest req, @RequestParam String dgCode, @RequestParam String sgguCode, @RequestParam String sgguEmdCode, @RequestParam String businessName, @RequestParam String businessRegNo) throws IOException {
		logger.debug("name {}", businessName);
		logger.debug("no {}", businessRegNo);

//		String businessname = req.getParameter(businessName);
		if (businessName == null || businessName.equals("")) {
			throw new RuntimeException("����� �̸��� �Է����ּ���");
		}
//		String businessregno= req.getParameter(businessRegNo);
		
		//�� businessname���� �ϸ� nullpointerError?
		//StringBuilder - String ������?
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getBassInfoSearch"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=aCOVuGWWqaeLEwzuClt8QJ4szRwIwuI6LSFCRuIdJDXBUrz9ljV9nAS4EevAS%2FDPJust7%2FM8CoKFqnlWz7YKgw%3D%3D"); /*Service Key*/
		urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_dg_cd","UTF-8") + "=" + URLEncoder.encode(dgCode, "UTF-8")); /*�õ�(������ġ�� ������ �ּ��ڵ� ����)*/
        urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_sggu_cd","UTF-8") + "=" + URLEncoder.encode(sgguCode, "UTF-8")); /*�ñ���(������ġ�� ������ �ּ��ڵ� ����)*/
        urlBuilder.append("&" + URLEncoder.encode("ldong_addr_mgpl_sggu_emd_cd","UTF-8") + "=" + URLEncoder.encode(sgguEmdCode, "UTF-8")); /*���鵿(������ġ�� ������ �ּ��ڵ� ����)*/
        urlBuilder.append("&" + URLEncoder.encode("wkpl_nm","UTF-8") + "=" + URLEncoder.encode(businessName, "UTF-8")); /*������*/
        urlBuilder.append("&" + URLEncoder.encode("bzowr_rgst_no","UTF-8") + "=" + URLEncoder.encode(businessRegNo, "UTF-8")); /*����ڵ�Ϲ�ȣ(�տ��� 6�ڸ�)*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*��������ȣ*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("50", "UTF-8")); /*�హ��*/
		
        URL url = new URL(urlBuilder.toString());
        logger.debug("url {}", url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode());
        
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        
        String xmlCode = sb.toString();
        
        //--------------------------------
    	List<BusinessInfoListVO> list = new ArrayList<BusinessInfoListVO>();
        try {
        	String result = xmlCode;
        	InputSource is = new InputSource(new StringReader(result));
        	org.w3c.dom.Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        	XPath xpath = XPathFactory.newInstance().newXPath();
        	
        	XPathExpression expression = xpath.compile("//items/item");
        	
        	NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
        	logger.debug("nodeList {}", nodeList.getLength());
        	
        	List<String> listSt = new ArrayList<String>();
        	for(int i=0; i<nodeList.getLength(); i++) {
//        		expression = xpath.compile("wkplNm");
        		Node date = (Node)xpath.compile("dataCrtYm").evaluate(nodeList.item(i), XPathConstants.NODE);
        		Node seq = (Node)xpath.compile("seq").evaluate(nodeList.item(i), XPathConstants.NODE);
        		Node name = (Node)xpath.compile("wkplNm").evaluate(nodeList.item(i), XPathConstants.NODE);
        		Node no = (Node)xpath.compile("bzowrRgstNo").evaluate(nodeList.item(i), XPathConstants.NODE);
        		Node addr = (Node)xpath.compile("wkplRoadNmDtlAddr").evaluate(nodeList.item(i), XPathConstants.NODE);
        		
        		BusinessInfoListVO businessInfoListVO = new BusinessInfoListVO();
        		
        		businessInfoListVO.setSeq(seq.getTextContent());
        		String nameString = name.getTextContent();
        		businessInfoListVO.setName(nameString);
        		businessInfoListVO.setNo(no.getTextContent());
        		businessInfoListVO.setAddr(addr.getTextContent());
        		businessInfoListVO.setDate(date.getTextContent());
        		
        		list.add(businessInfoListVO);
        	}

        }catch(Exception e){
        	System.out.println(e.getMessage());
        }
        
        Collections.sort(list, new ListComparator());
        
        for(int i=0; i<list.size(); i++) {
        	System.out.println("@@@@@"+list.get(i));
        }

        mav.addObject("list", list);
		mav.setViewName("/api/businessInfoList");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/business-info.do")
	public ModelAndView selectBusinessInfo(ModelAndView mav, @RequestParam String seq, @RequestParam(value="date") String date) throws IOException {
		logger.debug(seq);
		logger.debug(date);
		
	        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getDetailInfoSearch"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=aCOVuGWWqaeLEwzuClt8QJ4szRwIwuI6LSFCRuIdJDXBUrz9ljV9nAS4EevAS%2FDPJust7%2FM8CoKFqnlWz7YKgw%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("seq","UTF-8") + "=" + URLEncoder.encode(seq, "UTF-8")); /*�ĺ���ȣ*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/xml");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        System.out.println(sb.toString());
		
	        String xmlCode = sb.toString();

	      //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    		StringBuilder urlBuilderYm = new StringBuilder("http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getPdAcctoSttusInfoSearch"); /*URL*/
            urlBuilderYm.append("?" + URLEncoder.encode("ServiceKey","UTF-8") +  "=aCOVuGWWqaeLEwzuClt8QJ4szRwIwuI6LSFCRuIdJDXBUrz9ljV9nAS4EevAS%2FDPJust7%2FM8CoKFqnlWz7YKgw%3D%3D"); /*Service Key*/
            urlBuilderYm.append("&" + URLEncoder.encode("seq","UTF-8") + "=" + URLEncoder.encode(seq, "UTF-8")); /*�ĺ���ȣ*/
            urlBuilderYm.append("&" + URLEncoder.encode("data_crt_ym","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*���(yyyymm)*/
            URL urlYm = new URL(urlBuilderYm.toString());
            HttpURLConnection connYm = (HttpURLConnection) urlYm.openConnection();
            connYm.setRequestMethod("GET");
            connYm.setRequestProperty("Content-type", "application/xml");
            System.out.println("Response code: " + connYm.getResponseCode());
            BufferedReader rdYm;
            if(connYm.getResponseCode() >= 200 && connYm.getResponseCode() <= 300) {
                rdYm = new BufferedReader(new InputStreamReader(connYm.getInputStream()));
            } else {
                rdYm = new BufferedReader(new InputStreamReader(connYm.getErrorStream()));
            }
            StringBuilder sbYm = new StringBuilder();
            String lineYm;
            while ((lineYm = rdYm.readLine()) != null) {
                sbYm.append(lineYm);
            }
            rdYm.close();
            connYm.disconnect();
            
            String xmlCodeYm = sbYm.toString();
	        
	        List<BusinessInfoVO> list = new ArrayList<BusinessInfoVO>();
	        BusinessInfoVO businessInfoVO = new BusinessInfoVO();
	        
	        try {
	        	String result = xmlCode;
	        	InputSource is = new InputSource(new StringReader(result));
	        	org.w3c.dom.Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
	        	XPath xpath = XPathFactory.newInstance().newXPath();
	        	
	        	XPathExpression expression = xpath.compile("//body/item");
	        	
	        	NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
	        	
        		String resultYm = xmlCodeYm;
        		System.out.println("@@@@@@@@@@@@@@@@@"+resultYm);
	        	InputSource isYm = new InputSource(new StringReader(resultYm));
	        	org.w3c.dom.Document documentYm = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(isYm);
	        	
	        	XPathExpression expressionYm = xpath.compile("//items/item");
	        	
	        	NodeList nodeListYm = (NodeList) expressionYm.evaluate(documentYm, XPathConstants.NODESET);
	        	System.out.println(nodeListYm);
	        	
	        	for(int i=0; i<nodeListYm.getLength(); i++) {
	        		Node vldtVlKrnNm = (Node)xpath.compile("vldtVlKrnNm").evaluate(nodeList.item(i), XPathConstants.NODE);
	    			Node wkplJnngStcd = (Node)xpath.compile("wkplJnngStcd").evaluate(nodeList.item(i), XPathConstants.NODE);
	        		Node adptDt = (Node)xpath.compile("adptDt").evaluate(nodeList.item(i), XPathConstants.NODE);
	        		Node scsnDt = (Node)xpath.compile("scsnDt").evaluate(nodeList.item(i), XPathConstants.NODE);
	        		Node jnngpCnt = (Node)xpath.compile("jnngpCnt").evaluate(nodeList.item(i), XPathConstants.NODE);
	        		Node name = (Node)xpath.compile("wkplNm").evaluate(nodeList.item(i), XPathConstants.NODE);
	        		Node no = (Node)xpath.compile("bzowrRgstNo").evaluate(nodeList.item(i), XPathConstants.NODE);
	        		Node addr = (Node)xpath.compile("wkplRoadNmDtlAddr").evaluate(nodeList.item(i), XPathConstants.NODE);
	    			Node nwAcqzrCnt = (Node)xpath.compile("nwAcqzrCnt").evaluate(nodeListYm.item(i), XPathConstants.NODE);
	    			Node lssJnngpCnt = (Node)xpath.compile("lssJnngpCnt").evaluate(nodeListYm.item(i), XPathConstants.NODE);
	    			
	    			businessInfoVO.setVldtVlKrnNm(vldtVlKrnNm.getTextContent());
	        		if(wkplJnngStcd.getTextContent().equals("1")) {
	        			String code = "���";
	        			businessInfoVO.setWkplJnngStcd(code);
	        			businessInfoVO.setAdptDt(adptDt.getTextContent());
	        		} else if(wkplJnngStcd.getTextContent().equals("2")) {
	        			String code = "Ż��";
	        			businessInfoVO.setWkplJnngStcd(code);
	        			businessInfoVO.setScsnDt(scsnDt.getTextContent());
	        		}
	        		
	        		businessInfoVO.setJnngpCnt(jnngpCnt.getTextContent());
	        		businessInfoVO.setName(name.getTextContent());
	        		businessInfoVO.setNo(no.getTextContent());
	        		businessInfoVO.setAddr(addr.getTextContent());
	    			businessInfoVO.setNwAcqzrCnt(nwAcqzrCnt.getTextContent());
	    			businessInfoVO.setLssJnngpCnt(lssJnngpCnt.getTextContent());
	    			
	    			list.add(businessInfoVO);
	        	}
	        	
	        }catch(Exception e){
	        	System.out.println(e.getMessage());
	        }

	        System.out.println(list);
		mav.addObject("list", list);
		mav.setViewName("/api/businessInfo");
		return mav;
	}
	
	public class ListComparator implements Comparator<Object> {
	    @Override
	    public int compare(Object o1, Object o2) {
	        String testString1 = ((BusinessInfoListVO)o1).getDate();
	        String testString2 = ((BusinessInfoListVO)o2).getDate();
	        return testString2.compareTo(testString1);
	    }
	}

}


