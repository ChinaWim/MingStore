package ming.stop.biz.admin.web;

import ming.stop.base.BaseServlet;
import ming.stop.biz.admin.service.ProductService;
import ming.stop.biz.admin.service.impl.ProductServiceImpl;
import ming.stop.entity.Product;
import ming.stop.utils.CommonUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ming on 2017/9/1.
 */
@WebServlet(name = "ProductServlet",urlPatterns = {"/admin/product"})
public class ProductServlet extends BaseServlet {
    ProductService productService = new ProductServiceImpl();

    //显示
    public void show(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Product> list = productService.queryAllByPdate();
            request.setAttribute("productList",list);
            request.getRequestDispatcher("/admin/product/list.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除
    public void delete(HttpServletRequest request, HttpServletResponse response){
        try{
            String pid = request.getParameter("pid");
            productService.deleteProductById(pid);
            response.getWriter().write("删除成功！3秒后跳转首页。。。");
        }catch (Exception e) {
            try {
                response.getWriter().write("删除失败！3秒后跳转首页。。。");

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/product?action=show");
        }
        response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/product?action=show");
    }


    //增加
    public void add(HttpServletRequest request, HttpServletResponse response){
        try{
            Product product = new Product();
            product.setPid(CommonUtils.getUUID());
            product.setPdate(new Date());
            product.setPflag(0);//未下架
            Map<String,Object> productMap = new HashMap<>();

            String path_temp = request.getServletContext().getRealPath("temp");
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory(1024*1024,new File(path_temp));
            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
            fileUpload.setHeaderEncoding("UTF-8");
            fileUpload.setFileSizeMax(1024*1024*10);
            List<FileItem> list = (List<FileItem>)fileUpload.parseRequest(request);
            if(list != null){
                for (FileItem fileItem : list) {
                    //如果不是文件
                    if(fileItem.isFormField()){
                        String fileName = fileItem.getFieldName();
                        String value = fileItem.getString("UTF-8");
                        productMap.put(fileName,value);
                    }else{
                        String filename = fileItem.getName();
                        String path_store = request.getServletContext().getRealPath("upload/product");
                        product.setPimage("upload/product/"+filename);
                        FileUtils.copyInputStreamToFile(fileItem.getInputStream(),new File(path_store+"/"+filename));
                        fileItem.delete();
                    }
                }
                BeanUtils.copyProperties(product,productMap);

            }
            productService.saveProduct(product);
            System.out.println(product);
            response.getWriter().write("添加成功！三秒后跳转商品首页。。。。");
            response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/product?action=show");
        }catch (Exception e) {
            try {
                response.getWriter().write("添加失败！三秒后跳转商品首页。。。");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/product?action=show");
            e.printStackTrace();
        }
    }

    //编辑
    public void edit(HttpServletRequest request, HttpServletResponse response){
        try{
            Product product = new Product();
            Map<String,Object> productMap = new HashMap<>();

            String path_temp = request.getServletContext().getRealPath("temp");
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory(1024*1024,new File(path_temp));
            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
            fileUpload.setHeaderEncoding("UTF-8");
            fileUpload.setFileSizeMax(1024*1024*10);
            List<FileItem> list = (List<FileItem>)fileUpload.parseRequest(request);

            if(list != null){
                for (FileItem fileItem : list) {
                    //如果不是文件
                    if(fileItem.isFormField()){
                        String fileName = fileItem.getFieldName();
                        String value = fileItem.getString("UTF-8");
                        productMap.put(fileName,value);

                    }else{
                        String filename = fileItem.getName();
                        String path_store = request.getServletContext().getRealPath("upload/product");
                        product.setPimage("upload/product/"+filename);
                        FileUtils.copyInputStreamToFile(fileItem.getInputStream(),new File(path_store+"/"+filename));
                        fileItem.delete();
                    }
                }
                BeanUtils.copyProperties(product,productMap);
            }
            boolean b = productService.updateProduct(product);
            if(b) {
                response.getWriter().write("更新成功！三秒后跳转商品首页。。。。");
            }
            response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/product?action=show");
        }catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("更新失败！三秒后跳转商品首页。。。");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/product?action=show");
        }
    }


}
