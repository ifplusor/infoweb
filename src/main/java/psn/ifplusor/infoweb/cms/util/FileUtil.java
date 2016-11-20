package psn.ifplusor.infoweb.cms.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static final String URI_PREFIX_CMS = "cms://";
	
    private static String FILEDIR = "/home/infoweb";
    
    static {
    	Properties pps = new Properties();
    	InputStream in = null;
    	try {
    		in = FileUtil.class.getClassLoader().getResourceAsStream("conf/infoweb.properties");
    		pps.load(in);
			FILEDIR = pps.getProperty("cms.filedir");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
    	while (FILEDIR.endsWith("/") && FILEDIR.length() > 1)
        	FILEDIR = FILEDIR.substring(0, FILEDIR.length() - 1);

        File file = new File(FILEDIR);
        if (!file.exists()) {
        	logger.debug("make file: " + FILEDIR);
            file.mkdir();
        }
    }
    
    /**
     * 通过短路径取得文件符
     * 
     * @param path 短路径
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(String path) throws FileNotFoundException {
    	
        String fullPath = null;
        if (path.startsWith("/"))
        	fullPath = FILEDIR + path;
        else
        	fullPath = FILEDIR + "/" + path;
        
        File file = new File(fullPath);
        if (!file.exists()) {
        	throw new FileNotFoundException("不存在的目录或文件: " + fullPath);
        }
        
        return file;
    }
    
    public static Map<String, Object> fileList(String Base) throws FileNotFoundException {

    	Map<String, Object> map = new HashMap<String, Object>();
        List<String> folders = new ArrayList<String>();
        List<String> files = new ArrayList<String>();

        File folder = getFile(Base);
        
        if (folder.isDirectory()) {
        	File[] paths = new File(folder.getPath()).listFiles();
        	for (File file : paths) {
        		if(file.isDirectory()) {
        			folders.add(file.getName());
        		} else {
        			files.add(file.getName());
        		}
        	}
        	map.put("type", 1);
        	map.put("folders", folders);
        	map.put("files", files);
        } else {
        	// 不是已绝对路径指定的文件，不允许显示
        	if (!Base.startsWith("/")) {
        		throw new FileNotFoundException("不存在的目录或文件: " + folder.getPath());
        	}
        		
        	files.add(folder.getName());
        	map.put("type", 0);
        	map.put("files", files);
        }
            
        return map;
    }   
    
    public static String saveMultipartFile(MultipartFile file) throws IOException {
    	
    	String filename = file.getOriginalFilename();
    	String fullPath = initFilePath(filename);
    	
    	logger.debug("upload file \"" + filename + "\" to " + fullPath);
    	
    	OutputStream out = new FileOutputStream(fullPath);
        write(file.getInputStream(), out);
        
        logger.debug("upload \"" + fullPath + "\" seccussed.");

        return fullPath.replaceFirst(FILEDIR, URI_PREFIX_CMS);
    }
    
    /**
     * 上传
     * @param request
     * @throws IOException
     */
    public static void upload(HttpServletRequest request) throws IOException {
    	
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
        
        Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, MultipartFile> entry = it.next();
            MultipartFile mFile = entry.getValue();
                        
            if(mFile.getSize() != 0 && !"".equals(mFile.getName())){
            	saveMultipartFile(mFile);
            }
        }
    }
    
    private static String initFilePath(String filename) {
    	
    	String fullPath = FILEDIR + "/" + filename;
    	File file = new File(fullPath);
    	while (file.exists()) {
    		fullPath = file.getParent() + "/" + file.getName() + ".copy";
    		file = new File(fullPath);
    		logger.debug("make file: " + file.getPath());
    	}
    	
    	return fullPath;
    }
    
    public static void downloadByUri(String uri, ServletOutputStream out) throws FileNotFoundException {
    	if (uri.startsWith(URI_PREFIX_CMS)) {
    		download(uri.substring(URI_PREFIX_CMS.length()), out);  
    	}
    }
    
    public static void download(String path, ServletOutputStream out) throws FileNotFoundException {
    	FileInputStream in = new FileInputStream(getFile(path));
    	
        try {
            write(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 写入数据
     * @param in input stream for reading file
     * @param out out stream for writing file
     * @throws IOException exception occur when writing
     */
    private static void write(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                logger.warn(ex.getMessage());
            }
            try {
                out.close();
            } catch (IOException ex) {
                logger.warn(ex.getMessage());
            }
        }
    }

    public static String encodeFileName(String agent, String fileName) {

        String encodedFileName = null;

        try {
            if (null != agent && agent.contains("MSIE")) {//IE
                encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else if (null != agent && agent.contains("Mozilla")) {
                encodedFileName = new String (fileName.getBytes("UTF-8"), "iso-8859-1");
            } else {
                encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage());
        }

        return encodedFileName;
    }
}
