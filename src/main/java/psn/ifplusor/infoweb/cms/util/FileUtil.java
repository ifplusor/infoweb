package psn.ifplusor.infoweb.cms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	
    private static String FILEDIR = "/home/infoweb";
    
    static {
    	Properties pps = new Properties();
    	InputStream in = null;
    	try {
    		in = FileUtil.class.getClassLoader().getResourceAsStream("config/infoweb.properties");
    		pps.load(in);
			FILEDIR = pps.getProperty("application.filedir");
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
    
    public static File getFile(String path) throws FileNotFoundException {
    	
        String searchPath = null;
        if (path.startsWith("/"))
        	searchPath = FILEDIR + path;
        else
        	searchPath = FILEDIR + "/" + path;
        
        File file = new File(searchPath);
        if (!file.exists()) {
        	throw new FileNotFoundException("不存在的目录或文件: " + searchPath);
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
            	String filename = mFile.getOriginalFilename();
            	String path = initFilePath(filename);

            	logger.debug("upload file \"" + filename + "\" to " + path);
            	
            	OutputStream out = new FileOutputStream(path);
                write(mFile.getInputStream(), out);
                
                logger.debug("upload \"" + path + "\" seccussed.");
            }
        }
    }
    
    private static String initFilePath(String name) {
    	
    	String path = FILEDIR + "/" + name;
    	File file = new File(path);
    	while (file.exists()) {
    		path = file.getParent() + "/" + file.getName() + ".copy";
    		file = new File(path);
    		logger.debug("make file: " + file.getPath());
    	}
    	
    	return path;
    }
    
    public static void download(String fileName, ServletOutputStream out) {
        try {
            FileInputStream in = new FileInputStream(getFile(fileName));
            write(in, out);
        } catch (FileNotFoundException e) {
            try {
                FileInputStream in = new FileInputStream(getFile(new String(fileName.getBytes("iso-8859-1"),"utf-8")));
                write(in, out);
            } catch (IOException e1) {              
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }       
    }
    
    /**
     * 写入数据
     * @param in
     * @param out
     * @throws IOException
     */
    public static void write(InputStream in, OutputStream out) throws IOException{
        try{
            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } finally {
            try {
                in.close();
            }
            catch (IOException ex) {
            }
            try {
                out.close();
            }
            catch (IOException ex) {
            }
        }
    }   
}
