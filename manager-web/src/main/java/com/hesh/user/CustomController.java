package com.hesh.user;

import com.hesh.service.CustomerService;
import com.hesh.vo.user.Customer;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by gaods on 2017/8/7.
 */

@Controller
@RequestMapping("/customer")
public class CustomController {

    @Resource
    CustomerService customerService;

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response){

        try{
            response.setContentType("octets/stream");
            //response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-disposition","attachment;filename="+new Date().getTime()+".xls");
             OutputStream ops=response.getOutputStream();
 //            FileOutputStream fout=new FileOutputStream(new Date().getTime()+".xls");
            Integer pageCount = 200;//每次查询记录数
//            List<ZszhMonitorFailedEnterprise> enterprises = new ArrayList<ZszhMonitorFailedEnterprise>();
//            int currentPageNo = 0;
//            for(;;){
//                Map param = new HashMap();
//                param.clear();
//                param.put("fileName", fileName);
//                param.put("currentRecordNum",pageCount * currentPageNo ++);
//                List list = zszhMonitorFailedEnterpriseMapper.selectByFileName(param);
//                enterprises.addAll(list);
//                if(list == null || list.size() < pageCount){
//                    break;
//                }
//            }
            Customer customer1=new Customer();
          List<Customer> customerList=  customerService.getCustomerListForExport(customer1);

            if(customerList != null && customerList.size() > 0){
                int rowNUM = 0;
                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("客户经理列表");
                //create header
                String[] headers = new String[]{"工号","数量","已使用数量","状态"};
                HSSFRow headerRow = sheet.createRow(rowNUM ++);
                headerRow.setHeight((short) 600);


                HSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                cellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

                HSSFFont font = wb.createFont();
                font.setFontHeightInPoints((short)15);
                font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

                cellStyle.setFont(font);

                for(int i=0;i<headers.length;i++){
                    HSSFCell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(i,headers[i].getBytes().length * 2 * 256);
                }

                //create body
                Row row = null;
                int colNum = 0;
                Cell currentCell = null;
                for(Customer customer : customerList){
                    row = sheet.createRow(rowNUM ++);

                    currentCell = row.createCell(colNum++);
                    currentCell.setCellValue(customer.getPnCode());

                    currentCell = row.createCell(colNum++);
                    currentCell.setCellValue(customer.getZcCount()==null?0:customer.getZcCount());

                    currentCell = row.createCell(colNum++);
                    currentCell.setCellValue(customer.getSsCount()==null?0:customer.getSsCount());

                    currentCell = row.createCell(colNum++);
                    currentCell.setCellType(Cell.CELL_TYPE_STRING);
                    currentCell.setCellValue(customer.getPastate()==null?"":customer.getPastate());

                    colNum = 0;
                }
                //create footer

                try {
                    wb.write(ops);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


            ops.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
