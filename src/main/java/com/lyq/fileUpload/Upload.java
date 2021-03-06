package com.lyq.fileUpload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传功能有许多需要注意的小细节问题，以下列出的几点需要特别注意的
 * 　　1、为保证服务器安全，上传文件应该放在外界无法直接访问的目录下，比如放于WEB-INF目录下。
 * 　　2、为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名。
 * 　　3、为防止一个目录下面出现太多文件，要使用hash算法打散存储。
 * 　　4、要限制上传文件的最大值。
 * 　　5、要限制上传文件的类型，在收到上传文件名时，判断后缀名是否合法。
 */
@WebServlet("/fileUpload")
public class Upload extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //得到上传文件的保存目录，将上传的文件存放在WEB-INF目录下面 不允许外界直接访问，保证上传文件的安全
        String savePath = this.getServletContext().getRealPath("/FileLoad");
        String tempPath = this.getServletContext().getRealPath("/FileLoad/temp");
        File file = new File(tempPath);
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(tempPath + "目标目录不存在，需要进行创建");
            file.mkdirs();
        }
        String message = null;
        try {
            //1 创建DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置缓冲区得大小
            factory.setSizeThreshold(1024 * 1000000);
            //设置上传时生成得临时文件保存目录
            factory.setRepository(file);
            //2 创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //监听文件上传速度
            upload.setProgressListener(new ProgressListener() {
                @Override
                public void update(long pBytesRead, long pContentLength, int arg2) {
                    // System.out.println("文件大小为:" + pContentLength + "当前处理" + pBytesRead);
                }
            });
            //判断提交上来的数据是不是表单上的数据
            upload.setHeaderEncoding("UTF-8");
            if (!ServletFileUpload.isMultipartContent(req)) {
                return;
            }
            //设置上传文件总量得最大值
            upload.setSizeMax(10000 * 1024 * 1024);
            //设置单个上传文件得最大值
            upload.setFileSizeMax(1024 * 1000000);

            //4 使用ServletFileUpload解析器来解析上传数据，解析结果返回的是一个List<FileItem>
            //集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(req);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    System.out.println(name + " = " + value);
                } else {
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的
                    // 如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt

                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    //获得文件扩展名字
                    String fileExname = filename.substring(filename.lastIndexOf(".") + 1);
                    System.out.println("上传的文件的扩展名是：" + fileExname);
                    //获取item输入流
                    InputStream inputStream = item.getInputStream();
                    //得到保存文件得名称
                    String saveFilename = makeFileName(filename);
                    //得到文件得保存目录
                    String realSavaPath = makePath(saveFilename, savePath);
                    //创建一个文件输出流

                    FileOutputStream fileOutputStream = new FileOutputStream(realSavaPath
                            + "\\" + saveFilename);
                    //创建一个缓冲区
                    byte[] buffer = new byte[1024];
                    //判断输入流是否已经读完的标识
                    int len = 0;
                    while ((len = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    inputStream.close();
                    fileOutputStream.close();
                    // item.delete();
                    message = "文件上传成功";
                }
            }
        } catch (Exception e) {
            message = "文件上传失败";
            e.printStackTrace();
        }
        req.setAttribute("message", message);
        resp.sendRedirect("/success.jsp");
    }

    // 生成上传文件的文件名，文件名以:UUID标识符+"_"+文件的原始名称
    private String makeFileName(String filename) {
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString() + "_" + filename;
    }

    // 为防止目录下面出现太多文件，要使用hash算法打算存存储
    private String makePath(String filename, String savePath) {

        int hashcode = filename.hashCode();
        int dir1 = hashcode & 0xf;// 0--15
        // int dir2 = (hashcode&0xf0)>>4; // 0--15

        //构建新的保存目录
        String dir = savePath + "\\" + dir1;
        //File可以代表文件也可以代表目录
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
            System.out.println("创建新目录成功！路径地址为：" + dir);
        }
        return dir;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}