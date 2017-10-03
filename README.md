# 展示了下载文件的实现，展示了把文件打包成zip然后提供下载（在这个过程并不在服务端磁盘产生zip文件）
##验证方式
	http://localhost:8080/hello-req-zipairfile/debug/forward/download
	
## DownloadController实现了这两种情况