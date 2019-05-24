package com.sm.frame;

import com.sm.entity.Admin;
import com.sm.entity.CClass;
import com.sm.entity.Department;
import com.sm.factory.DAOFactory;
import com.sm.factory.ServiceFactory;
import com.sm.ui.ImgPanel;
import com.sm.utils.AliOSSUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.TimerTask;

public class AdminMainFrame extends JFrame {
    private JPanel rootPanel;
    private JButton 院系管理Button;
    private JButton 班级管理Button;
    private JButton 学生管理Button;
    private JButton 奖惩Button;
    private JPanel centerPanel;
    private JPanel departmentPanel;
    private JPanel classPanel;
    private JPanel studentPanel;
    private JPanel rewardPanel;
    private JPanel topPanel;
    private JButton 新增Button;
    private JButton 刷新Button;
    private JPanel leftPanel;
    private JTextField depNameField;
    private JButton 选择logo图Button;
    private JButton 确定Button;
    private JScrollPane scrollPane;
    private JPanel contentPanel;
    private JLabel adminNameLabel;
    private JLabel logoLabel;
    private JButton 取消Button;
    private JTextField CClassField;
    private JButton 新增班级Button;
    private JPanel treePanel;
    private JPanel classContentPanel;
    private JLabel timeLabel;
    private TimerTask clockTask;
    private Timer timer;
    private  Admin admin;
    private String uploadFileUrl;
    private File file;
    private JComboBox<Department> depcomboBox;
    private CClass cClass;
    private int departmentId = 0;
    private int getId;


    public AdminMainFrame(Admin admin) {
        this.admin=admin;
        adminNameLabel.setText("管理员："+admin.getAdminName());
        //窗体基本属性
        setTitle("管理员主界面");
        setContentPane(rootPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        showDepartments();
        //获取centerPanel的布局,获得是layoutManager，向下转型为cardLayout
        final CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
        院系管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card1");
            }
        });
        班级管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card2");
                showClassPanel();
            }
        });

        新增Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = leftPanel.isVisible();
                if (flag == true) {
                    leftPanel.setVisible(false);
                } else {
                    leftPanel.setVisible(true);
                }
            }
        });
        刷新Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDepartments();
            }
        });
        depNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                depNameField.setText("");
            }
        });
        depNameField.addFocusListener(new FocusAdapter() {
        });
        选择logo图Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        选择logo图Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("D:\\"));
                int result = fileChooser.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    //选中文件
                    file = fileChooser.getSelectedFile();
                    //通过文件创建icon对象
                   ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                   icon.setImage(icon.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
                    //通过标签显示图片
                    logoLabel.setIcon(icon);
                    //设置标签可见
                    logoLabel.setVisible(true);
                    //将按钮隐藏
                    选择logo图Button.setVisible(false);
                }
            }
        });

        确定Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //上传文件到OSS并返回外链
                uploadFileUrl = AliOSSUtil.ossUpload(file);
                //创建Department对象，并设置相应属性
                Department department = new Department();
                department.setDepartmentName(depNameField.getText().trim());
                department.setLogo(uploadFileUrl);
                //调用service实现新增功能
                int n = ServiceFactory.getDepartmentServiceInstance().addDepartment(department);
                if (n == 1) {
                    JOptionPane.showMessageDialog(rootPanel, "新增院系成功");
                    //新增成功后，将侧边栏隐藏
                    leftPanel.setVisible(false);
                    //刷新界面数据
                    showDepartments();
                    //将图片预览标签隐藏
                    logoLabel.setVisible(false);
                    //将选择图片的按钮可见
                    选择logo图Button.setVisible(true);
                    //清空文本框
                    depNameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(rootPanel, "新增院系失败");
                }
            }
        });
        取消Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("D:\\"));
                int result = fileChooser.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    //选中文件
                    file = fileChooser.getSelectedFile();
                    //通过文件创建icon对象
                    Icon icon = new ImageIcon(file.getAbsolutePath());
                    //通过标签显示图片
                    logoLabel.setIcon(icon);
                    //设置标签可见
                    logoLabel.setVisible(true);
                    //将按钮隐藏
                    选择logo图Button.setVisible(false);

                }
            }
        });
        depcomboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //得到选中项的索引
              int index = depcomboBox.getSelectedIndex();
                //按照索引取出项，就是一个Department对象，然后取出其id备用
               Department department = (Department) depcomboBox.getItemAt(index);
               departmentId = department.getId();


            }
        });
        新增班级Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CClass cClass = new CClass();
                cClass.setClassName(CClassField.getText().trim());
                cClass.setDepartmentId(departmentId);
                int n = ServiceFactory.getCClassServiceInstance().addCClass(cClass);
                if (n == 1){
                    JOptionPane.showMessageDialog(rootPanel,"新增班级成功");
                    showClassPanel();
                    CClassField.setText("");
                }else {
                    JOptionPane.showMessageDialog(rootPanel,"新增班级失败");
                }
            }
        });


    }


    private void showDepartments() {
        //移除原有数据
        contentPanel.removeAll();
        //从service层获取到所有院系列表
        List<Department> departmentList = ServiceFactory.getDepartmentServiceInstance().selectAll();
        int len = departmentList.size();
        int row = len % 4 == 0 ? len / 4 : len / 4 + 1;
        GridLayout gridLayout = new GridLayout(row, 4, 15, 15);
        contentPanel.setLayout(gridLayout);
        for (Department department : departmentList) {
            //给每个院系对象创建一个面板
            JPanel depPanel = new JPanel();
            depPanel.setPreferredSize(new Dimension(200, 200));
            //将院系名称设置给面板标题
            depPanel.setBorder(BorderFactory.createTitledBorder(department.getDepartmentName()));
            //新建一个Label用来放置院系logo，并指定大小
            JLabel logoLabel = new JLabel("<html><img src='" + department.getLogo() + "' width=200 height=200/></html>");
            JButton delBtn=new JButton("删除");
            delBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked (MouseEvent e) {
                    int n=JOptionPane.showConfirmDialog(null,"确定要删除这行数据吗？","删除警告",
                            JOptionPane.YES_OPTION);
                    if (n==JOptionPane.YES_OPTION) {
                        contentPanel.remove(depPanel);
                        contentPanel.repaint();
                        ServiceFactory.getDepartmentServiceInstance().deleteDepartment(department.getId());
                    }
                }
            });
            delBtn.setPreferredSize(new Dimension(100,50));
            //图标标签加入院系面板
            depPanel.add(logoLabel);
            depPanel.add(delBtn);
            //院系面板加入主体内容面板
            contentPanel.add(depPanel);
            //刷新主体内容面板
            contentPanel.revalidate();
        }

    }




    public  void showClassPanel(){
        List<Department> departmentlist=ServiceFactory.getDepartmentServiceInstance().selectAll();
        showCombobox(departmentlist);
        showTree(departmentlist);
        showClasses(departmentlist);
    }
    private void showCombobox(List<Department> departmentList){
        for (Department department:departmentList
        ) {
            depcomboBox.addItem(department);
        }
    }
    private void showTree(List<Department> departmentList){
        DefaultMutableTreeNode top=new DefaultMutableTreeNode("南工院");
        for (Department department :
                departmentList) {
            DefaultMutableTreeNode group=new DefaultMutableTreeNode(department.getDepartmentName());
            top.add(group);
            List<CClass> cClassList=ServiceFactory.getCClassServiceInstance().selectByDepartmentId(department.getId());
            for (CClass cClass :
                    cClassList) {
                DefaultMutableTreeNode node=new DefaultMutableTreeNode(cClass.getClassName());
                group.add(node);
            }
        }
        final JTree tree=new JTree(top);
        tree.setRowHeight(30);
        tree.setFont(new Font("宋体",Font.PLAIN,20));
        treePanel.add(tree);
        treePanel.revalidate();

    }
    private void showClasses(List<Department> departmentList){
        classContentPanel.removeAll();
        Font titleFont=new Font("微软雅黑",Font.PLAIN,20);
        for (Department department :
                departmentList) {
            ImgPanel depPanel=new ImgPanel();
            depPanel.setFileName("bg1.png");
            depPanel.repaint();
            depPanel.setPreferredSize(new Dimension(350,500));
            depPanel.setLayout(null);
            JLabel depNameLabel=new JLabel(department.getDepartmentName());
            depNameLabel.setFont(titleFont);
            depNameLabel.setBounds(100,15,200,30);
            List<CClass> cClassList=ServiceFactory.getCClassServiceInstance().selectByDepartmentId(department.getId());
            DefaultListModel listModel=new DefaultListModel();
            for (CClass cClass :
                    cClassList) {
                listModel.addElement(cClass);
                System.out.println(listModel);
            }
            JList<CClass> jList=new JList<>(listModel);
            JScrollPane listScrollPanel=new JScrollPane(jList);
            System.out.println(cClassList);
            listScrollPanel.setBounds(70,120,190,300);
            depPanel.add(depNameLabel);
            depPanel.add(listScrollPanel);
            classContentPanel.add(depPanel);
            JPopupMenu jPopupMenu = new JPopupMenu();
           JMenuItem item1 = new JMenuItem("修理");
           JMenuItem item2 = new JMenuItem("删除");
           jPopupMenu.add(item1);
           jPopupMenu.add(item2);
           jList.add(jPopupMenu);
           jList.addMouseListener(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                   int index = jList.getSelectedIndex();
                   if (e.getButton()==3){
                       jPopupMenu.show(jList,e.getX(),e.getY());
                       CClass cClass = jList.getModel().getElementAt(index);
                       item2.addActionListener(new ActionListener() {
                           @Override
                           public void actionPerformed(ActionEvent e) {
                               int choice = JOptionPane.showConfirmDialog(depPanel,"确定删除吗？");
                               if (choice==0){
                                   ServiceFactory.getCClassServiceInstance().deleteClassById(cClass.getId());
                                   listModel.remove(index);
                                   showTree(ServiceFactory.getDepartmentServiceInstance().selectAll());
                               }

                           }
                       });
                   }

               }
           });
        }


    }


    public static void main(String[] args) throws Exception{
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        new AdminMainFrame(DAOFactory.getAdminDAOInstance().getAdminByAccount("aaa@qq.com"));
    }
}
