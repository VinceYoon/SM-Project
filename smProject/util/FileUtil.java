package com.smProject.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import com.smProject.dao.util.CamelMap;

public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	
	public static String upload(MultipartFile file, String path, String fileRename) throws Exception {

		String uploadedFileName;
		try {
			if (file == null || file.isEmpty()) {
				throw new NullPointerException("EMPTY FILE");
			}

			String uploadPath = path + "/";
			File dir = new File(uploadPath);
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}

			String originalFileName = file.getOriginalFilename();
			if (StringUtil.isNotEmpty(originalFileName) && StringUtil.isNotEmpty(fileRename)) {
				originalFileName = changeFileName(originalFileName, fileRename);
			}

			// 중복된 파일이 있으면 _1 붙여서 처리
			originalFileName = appendSuffixName(uploadPath, originalFileName, 1);

			File savefile = new File(uploadPath, originalFileName);
			file.transferTo(savefile);

			uploadedFileName = savefile.getName();
			logger.info("!upload success! filepath: {}, orgFileName: {}, uploadedFileName: {}", uploadPath,
					file.getOriginalFilename(), uploadedFileName);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return uploadedFileName;
	}

	/**
	 * 파일 업로드
	 *
	 * @param file
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String upload(MultipartFile file, String path) throws Exception {

		return upload(file, path, null);
	}

	/**
	 * 파일명 변경
	 *
	 * @param orginFileName
	 * @param changeName
	 * @return
	 */
	public static String changeFileName(String orginFileName, String changeName) {

		int index = orginFileName.lastIndexOf(".");
		String fileExt = orginFileName.substring(index + 1);
		String newName = changeName + "." + fileExt;

		return newName;
	}

	/**
	 * 파일 내용 문자열로 반환
	 *
	 * @param fileName
	 * @return
	 */
	public static /* synchronized */ String read(String fileName) {
		return read(fileName, "UTF-8");
	}

	/**
	 * 파일 내용 문자열로 반환
	 *
	 * @param fileName
	 * @return
	 */
	public static /* synchronized */ String read(String fileName, String characterSet) {
		StringBuffer strBuf = new StringBuffer();
		boolean eof = false;
		try {
			BufferedReader bufReader = null;
			if (StringUtil.isEmpty(characterSet)) {
				FileReader file = new FileReader(fileName);
				bufReader = new BufferedReader(file);
			} else {
				bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), characterSet));
			}
			while (!eof) {
				String line = bufReader.readLine();
				if (line == null)
					eof = true;
				else
					strBuf.append(line).append(System.getProperty("line.separator"));
			}
			bufReader.close();
		} catch (Exception localException) {
		}
		return strBuf.toString();
	}

	/**
	 * 특정 경로의 파일 리스트 조회
	 *
	 * @param path
	 * @return
	 */
	public static List<File> fileList(String path) {

		File folder = null;
		try {
			folder = new File(path);
		} catch (Exception e) {
			return null;
		}

		File[] listOfFiles = folder.listFiles();

		if (CommonUtil.isEmpty(listOfFiles)) {
			return null;
		}

		List<File> fileList = new ArrayList<File>();
		for (File file : listOfFiles) {
			if (!file.isFile()) {
				continue;
			}

			fileList.add(file);
		}

		return fileList;
	}

	/**
	 * 파일 삭제
	 *
	 * @param path
	 */
	public static void deleteFile(String path) {
		new File(path).delete();
	}

	/**
	 * 중복 파일 확인해서 _1, _2 카운트 붙임
	 *
	 * @param orgFileName
	 * @param originalFileName
	 * @param seq
	 * @return
	 */
	public static String appendSuffixName(String path, String orgFileName, int seq) {
		String retFileName = "";
		// 파일이 존재하는지 확인한다.
		if (new File(path + orgFileName).exists()) {
			int plusSeq = 1;

			String seqStr = "_" + seq;
			String firstFileName = orgFileName.substring(0, orgFileName.lastIndexOf("."));
			String extName = orgFileName.substring(orgFileName.lastIndexOf("."));

			// 만약 파일명에 _숫자가 들어간경우라면..
			if (orgFileName.lastIndexOf("_") != -1 && !firstFileName.endsWith("_")) {
				String numStr = orgFileName.substring(orgFileName.lastIndexOf("_") + 1,
						orgFileName.lastIndexOf(extName));
				try {
					plusSeq = Integer.parseInt(numStr);
					plusSeq = plusSeq + 1;

					retFileName = firstFileName.substring(0, firstFileName.lastIndexOf("_")) + "_" + plusSeq + extName;
				} catch (NumberFormatException e) {
					retFileName = firstFileName + seqStr + extName;
					return appendSuffixName(path, retFileName, ++plusSeq);
				}

			} else {
				retFileName = firstFileName + seqStr + extName;
			}
			// 재귀
			return appendSuffixName(path, retFileName, ++plusSeq);
		} else {
			return orgFileName;
		}
	}

	/**
	 * 확장자를 제외한 파일명 리턴
	 *
	 * @param fileName
	 * @return
	 */
	public static String exceptExtFileNm(String fileName) {

		if (StringUtil.isEmpty(fileName)) {
			return "";
		}

		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	
	/**
	 * path 물리 경로 : constant.properties에 profile.file.physical.path
	 * @param path
	 * @return
	 */
	public static List<CamelMap> getFileList(String path) {

		File folder = null;
		try {
			folder = new File(path);
		} catch (Exception e) {
			return null;
		}

		File[] listOfFiles = folder.listFiles();

		if (CommonUtil.isEmpty(listOfFiles)) {
			return null;
		}

		List<CamelMap> fileList = new ArrayList<CamelMap>();
		for (File file : listOfFiles) {
			CamelMap map = new CamelMap();
			map.put("name", file.getName());
			fileList.add(map);
		}

		System.out.println("fileList ::>>" + fileList.size());
		
		return fileList;
	}
	
}
