package com.hcctech.bookshelf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

@SuppressWarnings("unused")
public class FtpBeanUtil {

	public static long KEEP_ALIVE_TIME = 10 * 1000;

	public static int KEEP_CONNECTION_TIME = 10 * 1000;

	public static int KEEP_DATASTREAM_TIME = 10 * 1000;

	private static int BUFFER_SIZE = 100*1024;
	
	public static String ENCODE_TYPE = "UTF-8";// 默认编码

	private static String FTP_ENCODING_LIST = "";

	private String host = DomainUtil.getFtpserver();
	private String userName = DomainUtil.getFtpuser();
	private String password = DomainUtil.getFtppwd();
	private int port = Integer.valueOf(DomainUtil.getFtpport());

	private FTPClient ftpClient = null;
	
	public void setHost(String host) {
		this.host = host;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}

	private String rootPath;

	/**
	 * 建立连接
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public void createFTPClient() throws SocketException, IOException {
		ftpClient = new FTPClient();
		if (host == null || host.equals("")) {
			return;
		}
		ftpClient.setControlEncoding(ENCODE_TYPE);
		ftpClient.setDataTimeout(KEEP_DATASTREAM_TIME);
		try {
			ftpClient.connect(host, port);
		} catch (Exception e1) {
			try {
				ftpClient.disconnect();
			} catch (Exception e) {
			}
		}
		if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
			return;
		try {
			boolean state = ftpClient.login(userName, password);
			rootPath = ftpClient.printWorkingDirectory();
			if (!state) {
				shutdown();
				return;
			}
		} catch (Exception e) {
			shutdown();
		}
		return;
	}

	/**
	 * 创建目录
	 * 
	 * @param remote
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public boolean createDirecroty(String remote) throws SocketException,
			IOException {
		if (ftpClient != null) {
			if (remote == null || remote.equals("")) {
				// logger.warn("the remote directory to create is null.");
				return false;
			}
			if (!remote.startsWith("/")) {
				// logger.warn("the remote directory must start with / .");
				return false;
			}
			if (!remote.endsWith("/")) {
				// logger.warn("the remote directory must end with / .");
				return false;
			}
			changeToFtpRoot();
			String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
			if (!directory.equalsIgnoreCase("/")
					&& !ftpClient.changeWorkingDirectory(new String(directory
							.getBytes(ENCODE_TYPE), "iso-8859-1"))) {
				// 如果远程目录不存在，则递归创建远程服务器目录
				int start = 0;
				int end = 0;
				if (directory.startsWith("/")) {
					start = 1;
				} else {
					start = 0;
				}
				end = directory.indexOf("/", start);
				while (true) {
					String subDirectory = new String(directory.substring(start,
							end).getBytes(ENCODE_TYPE), "iso-8859-1");
					boolean s = ftpClient.changeWorkingDirectory(subDirectory);
					if (!s) {
						if (ftpClient.makeDirectory(subDirectory)) {
							ftpClient.changeWorkingDirectory(subDirectory);
						} else {
							// logger.info("create directory failed.");
							return false;
						}
					}
					start = end + 1;
					end = directory.indexOf("/", start);
					// 检查所有目录是否创建完毕
					if (end <= start) {
						break;
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 文件上传
	 * 
	 * @param remotePathFile
	 * @param file
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public boolean upload(String remotePathFile, File file)
			throws SocketException, IOException {
		InputStream inputstream = null;
		try {
			inputstream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
		}
		if (inputstream == null) {
			return false;
		}
		return upload(remotePathFile, inputstream);
	}

	/**
	 * 文件上传（流）
	 * 
	 * @param remotePathFile
	 * @param inputstream
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public boolean upload(String remotePathFile, InputStream inputstream)
			throws SocketException, IOException {
		boolean result = false;
		if (ftpClient == null) {
			return result;
		}
		if (StringUtils.isEmpty(remotePathFile)) {
			return result;
		}
		if (!remotePathFile.startsWith("/")) {
			remotePathFile = "/" + remotePathFile;
		}
		if (remotePathFile.endsWith("/")) {
			return result;
		}
		try {
			// 如果路径不存在，自动创建上传路径
			String path = remotePathFile.substring(0,
					remotePathFile.lastIndexOf("/") + 1);
			if (!existDirectory(path)) {
				createDirecroty(path);
				// if (isSucced) {'
				// 成功
				// } else {
				// }
			}
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			remotePathFile = changeToFtpRoot() + remotePathFile;
			remotePathFile = new String(remotePathFile.getBytes(ENCODE_TYPE),
					"ISO-8859-1");
			ftpClient.enterLocalPassiveMode();
//			if(existsFile(remotePathFile)){
//				deleteFile(remotePathFile);
//			}
			boolean uploadstate = ftpClient.storeFile(remotePathFile,
					inputstream);
			inputstream.close();
			return uploadstate;
		} catch (IOException e) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e1) {
					// logger.warn("FtpUtil.class-socket error:" +
					// e1.getMessage());
				}
			}
			return false;
		}
	}

	/**
	 * <b> 下载文件</b>
	 * <pre> 
	 * OutputStream os=null;
     *      try {                
     *      	os = response.getOutputStream();
     *      	response.reset();
     *	download("文件名.txt", "/upload/123.txt",os,response);
     *...
     *          </pre>
	 * @param filename 保存的文件名
	 * @param remotePathFile 远程文件地址
	 * @param out 输出流
	 * @param response 
	 */
	public void download(String filename, String remotePathFile,
			OutputStream out, HttpServletResponse response) {
		if (ftpClient == null)
			return;
		try {
			if (remotePathFile == null || remotePathFile.equals("")) {
				return;
			}
			if (remotePathFile.endsWith("/")) {
				return;
			}
			remotePathFile = changeToFtpRoot() + remotePathFile;
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 编码路径
			remotePathFile = encodeFilePath(remotePathFile);
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(filename, "utf-8"));
			if (!StringUtils.isEmpty(remotePathFile)) {
				ftpClient.retrieveFile(remotePathFile, out);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e1) {
				}
			}
		}
	}
	

	
	/**
	 * 删除目录
	 * @param name
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public boolean deleteDirectory(String name) throws SocketException, IOException {
		if (ftpClient == null) {
			return false;
		}
		try {
			if (name == null || name.equals("")) {
				return false;
			}
			name = encodeDirectoryPath(name);
			if (!StringUtils.isEmpty(name)) {
				changeToFtpRoot();
				return ftpClient.removeDirectory(name);
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}
	/**
	 * 删除文件夹 包括下面的文件
	 * @return
	 */
	public boolean deleteDirectoryAndFile(String name) {
		if (ftpClient == null) {
			return false;
		}
		try {
			if (name == null || name.equals("")) {
				return false;
			}
			name = encodeDirectoryPath(name);
			if (!StringUtils.isEmpty(name)) {
				changeToFtpRoot();
				return doDelete(name);
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * @param name
	 * @param issuc
	 * @throws IOException
	 */
	private boolean doDelete(String name) throws IOException {
		boolean issuc = true;
		//if(name.substring(0,))
		FTPFile[] files= ftpClient.listFiles(name);
		 for (int i = 0; i < files.length; i++) {
			 if(!files[i].getName().equals(".")&&!files[i].getName().equals("..")){
				 if(files[i].isFile()){
					 issuc = issuc&&ftpClient.deleteFile(name+"/"+files[i].getName());
				 }else{
					 issuc = issuc&&doDelete(name+"/"+files[i].getName()); 
				 }
			 }
		}
		issuc = issuc&&ftpClient.removeDirectory(name);
		return issuc;
	}
	/**
	 * 删除文件
	 * @param filename 文件路径
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public boolean deleteFile(String filename) throws SocketException, IOException {
		try {
			if (ftpClient == null) {
				return false;
			}
			filename = encodeFilePath(filename);
			if (!StringUtils.isEmpty(filename)) {
				return ftpClient.deleteFile(filename);
			} else {
				return false;
			}
		} catch (IOException e) {
		}
		return false;
	}
	/**
	 * 目录路径 编码
	 * @param path
	 * @return
	 */
	public String encodeDirectoryPath(String path) {
		
//			if (StringUtils.isEmpty(FTP_ENCODING_LIST)) {
//				try {
//					String encodedPath = new String(path.getBytes(ENCODE_TYPE),
//							"ISO-8859-1");
//					if (existDirectory(encodedPath)) {
//						return encodedPath;
//					} else {
//						return null;
//					}
//				} catch (UnsupportedEncodingException e) {
//					return null;
//				}
//			}
//
//			String[] encodelist = FTP_ENCODING_LIST.split(",");
//			for (String encode : encodelist) {
//				try {
//					String encodedPath = new String(path.getBytes(encode),
//							"ISO-8859-1");
//					if (existDirectory(encodedPath)) {
//						return encodedPath;
//					}
//				} catch (UnsupportedEncodingException e) {
//				}
//			}
			return path;
		}
	/**
	 * 获取正确的编码路径
	 * 
	 * @param filepath
	 * @return
	 */
	public String encodeFilePath(String filepath) {
		if (StringUtils.isEmpty(FTP_ENCODING_LIST)) {
//			try {
//				String encodedPath = new String(filepath.getBytes(ENCODE_TYPE),
//						"ISO-8859-1");
//				if (existsFile(encodedPath)) {
					return filepath;
//				} else {
//					return null;
//				}
//			} catch (UnsupportedEncodingException e) {
//				return null;
//			}
		}

		String[] encodelist = FTP_ENCODING_LIST.split(",");
		for (String encode : encodelist) {
			try {
				String encodedPath = new String(filepath.getBytes(encode),
						"ISO-8859-1");
				if (existsFile(encodedPath)) {
					return encodedPath;
				}
			} catch (UnsupportedEncodingException e) {
			}
		}
		return null;
	}

	/**
	 * @param filepath
	 * @return
	 */
	public boolean existsFile(String filepath) {
		boolean newClient = false;
		if (ftpClient == null) {
			try {
				createFTPClient();
				newClient = true;
			} catch (SocketException e) {
				return false;
			} catch (IOException e) {
				return false;
			}
			if (ftpClient ==null)
				return false;
		}
		if (StringUtils.isEmpty(filepath)) {
			return false;
		}
		if (!filepath.startsWith("/")) {
			filepath = "/" + filepath;
		}
		String path = filepath.substring(0, filepath.lastIndexOf("/") + 1);
		String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
		try {
			ftpClient.changeWorkingDirectory(rootPath + path);
			// 如果重命名成功则说明文件存在，否则文件不存在
			return ftpClient.rename(filename, filename);
		} catch (IOException e) {
			return false;
		}finally{
			if(newClient)
				shutdown();
		}
	}
	/**
	 * 远程目录是否存在
	 * @param ftpClient
	 * @param remotepath
	 * @return
	 */
	public boolean existDirectory(String remotepath) {
		try {
			if (remotepath == null || remotepath.equals("")) {
				return false;
			}
			if (!remotepath.startsWith("/")) {
				remotepath = "/" + remotepath;
			}
			if (remotepath.equals("/")) {
				return true;
			}
			if (remotepath.endsWith("/")) {
				remotepath = remotepath.substring(1,
						remotepath.lastIndexOf("/"));
			}
			changeToFtpRoot();
			return ftpClient == null ? false : (ftpClient
					.changeWorkingDirectory("" + remotepath));
		} catch (IOException e) {
			return false;
		}
	}	
	public String changeToFtpRoot() {
		try {
			// 切换到当前目录
			ftpClient.changeWorkingDirectory(rootPath);
			return ftpClient.printWorkingDirectory();
		} catch (IOException e) {
			// logger.warn("IOException:FtpUtil:" + e.getMessage());
		}
		return "";
	}

	/**
	 * 远程执行命令
	 * @param ftpClient
	 * @param cmd
	 */
	public void exeservercmd(String cmd){
		if (ftpClient != null) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.sendSiteCommand(cmd);
				} catch (IOException e) {
					
				}
			}
		}
	}

	
	/**
	 * 释放资源
	 * @param ftpClient
	 */
	public void shutdown() {
		if (ftpClient != null) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
					ftpClient.disconnect();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 获取当前FTPClient
	 * @return
	 */
	public FTPClient getFtpClient() {
		return ftpClient;
	}
	
	
	/**
	 * 把文件解压到ftp
	 * @param zipFileSrc
	 * @param outputDirectory
	 * @return
	 */
	public Boolean unZip(File f,String outputDirectory){
		Boolean flag = false;
//		try{ 
//			ZipFile zipFile = new ZipFile(f);
//			Enumeration<ZipEntry> enumeration = zipFile.getEntries();
//			ZipEntry zipEntry = null;
//			while(enumeration.hasMoreElements()){
//				zipEntry = (ZipEntry)enumeration.nextElement();
//				//zip包里的文件是文件夹
//				if(zipEntry.isDirectory()){
//					String directoryName = zipEntry.getName();
//					directoryName = directoryName.substring(0,directoryName.length()-1);
//					createDirecroty(outputDirectory+"/"+directoryName);
//				}else{//zip包里的文件是文件
//					String fileName = zipEntry.getName();
//					fileName = fileName.replace("\\", "/");
//					
//					if(fileName.indexOf("/")!=-1){
//						createDirecroty(outputDirectory+"/"+fileName.substring(0, fileName.lastIndexOf ("/")));
//						fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length()); 
//					}
//					InputStream inputStream = zipFile.getInputStream(zipEntry);
//					if(fileName.indexOf(".html")>0||fileName.indexOf(".htm")>0){
//						BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
//						String _filename = IPTimeStamp.getTimeStamp();
//						_filename = this.getClass().getResource("/").getPath() + _filename + ".tmp";
//						_filename = _filename.replace("%20", " ");
//						File outfile = new File(_filename);
//						outfile.createNewFile();
//						FileOutputStream outputStream = new FileOutputStream(outfile);
//						OutputStreamWriter outputStreamWriter =  new OutputStreamWriter(outputStream, "utf-8");
//						BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
//						PrintWriter out = new PrintWriter(bufferedWriter);
//						String reading = "";
//						String newstr=null;
//						while ((reading = in.readLine()) != null) {
//							newstr = reading.replaceAll("<head>", "<head><script>document.domain = 'ipep.com';</script>");
//							if(reading.equals(newstr)){
//								newstr = reading.replaceAll("<HEAD>", "<head><script>document.domain = 'ipep.com';</script>");
//							}
//							out.println(newstr);
//						}
//						out.close();
//						bufferedWriter.close();
//						outputStreamWriter.close();
//						outputStream.close();
//						in.close();
//						//;
//						upload(outputDirectory+"/"+zipEntry.getName(), new FileInputStream(outfile));
//						outfile.delete();
//					}else{
//						upload(outputDirectory+"/"+zipEntry.getName(), inputStream);
//					}
//					if(inputStream!=null){
//						inputStream.close();
//					}
//				}
//			}
//			zipFile.close();
//			flag = true;
//		}catch(IOException ioe){
//			ioe.printStackTrace();
//		}
		return flag;
	}
}
