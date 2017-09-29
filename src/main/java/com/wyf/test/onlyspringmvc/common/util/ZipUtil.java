package com.wyf.test.onlyspringmvc.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

/**
 * 通过Java的Zip输入输出流实现压缩和解压文件
 * 
 * @author liujiduo
 * 
 */
public final class ZipUtil {

	/**
	 * 压缩文件
	 * 
	 * @param filePath
	 *            待压缩的文件路径
	 * @return 压缩后的文件
	 */
	public static File zip(String filePath) {
		File target = null;
		File source = new File(filePath);
		if (source.exists()) {
			// 压缩文件名=源文件名.zip
			String zipName = source.getName() + ".zip";
			target = new File(source.getParent(), zipName);
			if (target.exists()) {
				target.delete(); // 删除旧的文件
			}
			FileOutputStream fos = null;
			ZipOutputStream zos = null;
			try {
				fos = new FileOutputStream(target);
				zos = new ZipOutputStream(new BufferedOutputStream(fos));
				// 添加对应的文件Entry
				addEntry("/", source, zos);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				IOUtils.closeQuietly(zos);
				IOUtils.closeQuietly(fos);
			}
		}
		return target;
	}

	/**
	 * 扫描添加文件Entry
	 * 
	 * @param base
	 *            基路径
	 * 
	 * @param source
	 *            源文件
	 * @param zos
	 *            Zip文件输出流
	 * @throws IOException
	 */
	private static void addEntry(String base, File source, ZipOutputStream zos) throws IOException {
		// 按目录分级，形如：/aaa/bbb.txt
		String entry = base + source.getName();
		if (source.isDirectory()) {
			for (File file : source.listFiles()) {
				// 递归列出目录下的所有文件，添加文件Entry
				addEntry(entry + "/", file, zos);
			}
		} else {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				byte[] buffer = new byte[1024 * 10];
				fis = new FileInputStream(source);
				bis = new BufferedInputStream(fis, buffer.length);
				int read = 0;
				zos.putNextEntry(new ZipEntry(entry));
				while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
					zos.write(buffer, 0, read);
				}
				zos.closeEntry();
			} finally {
				IOUtils.closeQuietly(bis);
				IOUtils.closeQuietly(fis);
			}
		}
	}

	/**
	 * 解压文件
	 * 
	 * @param filePath
	 *            压缩文件路径
	 */
	public static void unzip(String filePath) {
		File source = new File(filePath);
		if (source.exists()) {
			ZipInputStream zis = null;
			BufferedOutputStream bos = null;
			try {
				zis = new ZipInputStream(new FileInputStream(source));
				ZipEntry entry = null;
				while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
					File target = new File(source.getParent(), entry.getName());
					if (!target.getParentFile().exists()) {
						// 创建文件父目录
						target.getParentFile().mkdirs();
					}
					// 写入文件
					bos = new BufferedOutputStream(new FileOutputStream(target));
					int read = 0;
					byte[] buffer = new byte[1024 * 10];
					while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
						bos.write(buffer, 0, read);
					}
					bos.flush();
				}
				zis.closeEntry();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				IOUtils.closeQuietly(zis);
				IOUtils.closeQuietly(bos);
			}
		}
	}

	/**
	 * 把一系列文件打包成zip
	 * 
	 * @param srcPathList
	 *            文件列表
	 * @param zipPath
	 *            完整路径名，包括文件名和扩展名
	 * @return 返回File，可以getAbsolutePath()获取到详细路径，或进一步获取到文件流
	 * @author Stone
	 */
	public static File zip(List<String> srcPathList, String zipPath) {

		for (String srcPath : srcPathList) {
			File source = new File(srcPath);
			if (!source.exists()) {
				return null;
			}
		}

		File target = new File(zipPath);
		// 压缩文件名=源文件名.zip
		if (target.exists()) {
			target.delete(); // 删除旧的文件
		}
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(target);
			zos = new ZipOutputStream(new BufferedOutputStream(fos));
			// 添加对应的文件Entry
			for (String srcPath : srcPathList) {
				File source = new File(srcPath);
				addEntry("/", source, zos);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(zos);
			IOUtils.closeQuietly(fos);
		}
		return target;
	}

//	public static File getZipStream(List<String> srcPathList) {
//
//		for (String srcPath : srcPathList) {
//			File source = new File(srcPath);
//			if (!source.exists()) {
//				return null;
//			}
//		}
//
//		FileOutputStream fos = null;
//		ZipOutputStream zos = null;
//		try {
//			fos = new FileOutputStream(target);
//			zos = new ZipOutputStream(new BufferedOutputStream(fos));
//			// 添加对应的文件Entry
//			for (String string : srcPathList) {
//
//			}
//			for (String srcPath : srcPathList) {
//				File source = new File(srcPath);
//				addEntry("/", source, zos);
//			}
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		} finally {
//			IOUtils.closeQuietly(zos);
//			IOUtils.closeQuietly(fos);
//		}
//		return target;
//	}

	public static void main(String[] args) {
		// String targetPath = "D:\\旧文档";
		// File file = ZipUtil.zip(targetPath);
		// System.out.println(file);
		// ZipUtil.unzip("F:\\Win7壁纸.zip");
//		String file1 = "E:/DevFolder/workspaces/ws64bit/java-base-learning/src/main/resources/pmml_model_保险.pmml";
//
//		List<String> srcPathList = Arrays.asList(//
//				"E:/DevFolder/workspaces/ws64bit/java-base-learning/src/main/resources/pmml_model_保险.pmml", //
//				"E:/DevFolder/workspaces/ws64bit/java-base-learning/src/main/resources/pmml_model_健康.pmml", //
//				"E:/DevFolder/workspaces/ws64bit/java-base-learning/src/main/resources/pmml_model_理财.pmml", //
//				"E:/DevFolder/workspaces/ws64bit/java-base-learning/src/main/resources/pmml_model_心理.pmml", //
//				"E:/DevFolder/workspaces/ws64bit/java-base-learning/src/main/resources/pmml_model_育儿.pmml", //
//				"E:/DevFolder/workspaces/ws64bit/java-base-learning/src/main/resources/pmml_model_职场.pmml"
//		//
//		);
//		String zipPath = "D:/myzip.zip";
//
//		File out = zip(srcPathList, zipPath);
//		System.out.println(out);
		
		
		try {
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream("D:/1.zip")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}