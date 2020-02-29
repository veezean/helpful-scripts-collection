import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author admin
 *
 */
public class UrlScreenCapture {
	private static final String TEMPLATE_JS_PATH = "NetToPdfMoban.js";
	private static final String EXE_PATH = "phantomjs\\phantomjs.exe";
	private static final String TMP_JS_PATH = "tmp\\";
	private static final String RESULT_PATH = "result\\";
	private static final String URL_PATH = "urls.txt";
	
	/**
	 * 将url内容转换为png图片保存
	 * @param url 目标url地址
	 * @param pngName 图片保存位置
	 */
	public void convertHtml2Png(String url, String name) {
		// 读取js模板
		// String templateJsContent = readFile(TEMPLATE_JS_PATH);

		String resultFilePath = RESULT_PATH + name + ".pdf";
		// 将js模板中的url和图片路径占位符全部替换为实际的
		//String realJsContent = templateJsContent
		//		.replace("url_placeholder", url)
		//		.replace("png_name_placeholder", resultFilePath);
		//String realJsTempPath = TMP_JS_PATH + UUID.randomUUID().toString() + ".js";
		// js内容写入临时文件
		//writeFile(realJsTempPath, realJsContent);

		// 拼接cmd命令并执行
		String cmd = EXE_PATH + " \"" + TEMPLATE_JS_PATH + "\" \"" + url + "\" \"" + resultFilePath + "\"";
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			int waitFor = process.waitFor();
			System.out.println(cmd);
			// 删除临时文件
			//new File(realJsTempPath).deleteOnExit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public List<String> loadUrls(String urlPath) {
		List<String> urls = new ArrayList<String>();
		
		BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(urlPath));
            String line = null;
            while ((line = br.readLine()) != null) {
            	if (!"".equals(line)) {
                   urls.add(line);
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuitely(br);
        }
		
		return urls;
	}
	
	private String readFile(String file) {
		String output = "";
		
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			int oneChar;

			while ((oneChar = input.read()) != -1) {
				buffer.append((char)oneChar);
			}
				
			output = buffer.toString();

			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}
	
	private int writeFile(String path, String content) {
		int flag = 1;
		String filename = path;
		
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"));
			bw.write(content);
			bw.close();
			flag = 1;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			flag = 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			flag = -1;
		} catch (IOException e) {
			e.printStackTrace();
			flag = -2;
		}
		
		return flag;
	}

    private static void closeQuitely(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new File(RESULT_PATH).mkdirs();
		new File(TMP_JS_PATH).mkdirs();
		
		UrlScreenCapture capture = new UrlScreenCapture();
		List<String> urls = capture.loadUrls(URL_PATH);
		for (String url : urls) {
			String[] s = url.split(">");
            if (s.length > 1) {
			    capture.convertHtml2Png(s[0], s[1]);
            } else {
                capture.convertHtml2Png(s[0], UUID.randomUUID().toString());
            }
		}
	}
	
	

}
