package com.sout.carcre.service;

import com.sout.carcre.controller.bean.QueryChip;
import com.sout.carcre.controller.bean.SynCard;
import com.sout.carcre.controller.bean.beanson.ChipCase;
import com.sout.carcre.controller.bean.beanson.ChipInfo;
import com.sout.carcre.controller.bean.beanson.ChipNum;
import com.sout.carcre.mapper.CardInfoMapper;
import com.sout.carcre.mapper.GradeListMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.GradeList;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.service.bean.ChipCollInfo;
import com.sout.carcre.service.bean.ChipFromCard;
import com.sout.carcre.service.bean.ChipFullInfo;
import com.sout.carcre.service.bean.UserGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class CardService {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    CardInfoMapper cardInfoMapper;
    @Autowired
    GradeListMapper gradeListMapper;

    /**
     * 随机获取碎片
     * @param userId
     * @param mileage
     * @return
     */
    public QueryChip querychip(String userId,int mileage){
        System.out.println("querychip");
        /*返回数据*/
        QueryChip queryChip=new QueryChip();

        //获得里程数对应的卡片等级
        int height=cardHeightByMile(mileage);

        //根据等级获得碎片信息
        List<ChipFromCard> chiplist=cardInfoMapper.seleteChipByheight(height);

        System.out.println("chiplist");

        //获得随机数 randNum
        int sumnum=chiplist.size()*5;
        Random random=new Random();
        int randNum=random.nextInt(sumnum)+1;

        //根据随机数获得具体获得的碎片信息
        int listindex=randNum/5;
        int interindex=randNum%5;

        /*获取的碎片详细信息*/
        ChipFullInfo chipFullInfo=new ChipFullInfo();
        ChipFromCard chipFromCard=chiplist.get(listindex-1);
        chipFullInfo.setCardId(chipFromCard.getCardId());
        //获取随机数下的碎片信息
        String ranchip=chipFromCard.getCardContent().split(",")[interindex];
        //获取到碎片信息分割：分别获取碎片ID以及碎片图片路径
        String shipId=ranchip.split(":")[0];
        String shippath=ranchip.split(":")[1];
        chipFullInfo.setChipId(shipId);
        chipFullInfo.setChipPath(shippath);
        System.out.println("已经获取碎片信息，将要查找信息："+chipFullInfo);
        //将获取到的碎片信息送入数据库（二分查找）
        UserInfo userInfo=userInfoMapper.selectUserInfoByUserId(Integer.parseInt(userId));
        String[] chipinfo=userInfo.getUserPiece().split(",");
        String data=chipFullInfo.getCardId()+":"+chipFullInfo.getChipId()+":1";
        System.out.println(data);
        /*新的碎片在碎片信息中的位置*/
//        int index=binsearch(chipinfo,0,chipinfo.length-1,data);

        /*插入新数组*/
        String[] newchipinfo=insertData(chipinfo,data);
       for(String s:newchipinfo){
           System.out.println(s);
       }
        /*合并卡片数据*/
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<newchipinfo.length;i++){
            if(i!=newchipinfo.length-1){
                stringBuilder.append(newchipinfo[i]).append(",");
            }else stringBuilder.append(newchipinfo[i]);
        }
        /*更新数据库信息*/
        userInfoMapper.updateChipInfoByuserId(Integer.parseInt(userId),stringBuilder.toString());

        //判断新的碎片数组中是否有合成卡片的可能
        boolean isnewcard=isNewCard(newchipinfo,String.valueOf(chipFullInfo.getCardId()));

        queryChip.setCardIsSyn(isnewcard);
        //构建碎片信息
        ChipInfo chipInfo=new ChipInfo();
        chipInfo.setChipHeight(height);
        chipInfo.setChipPath(chipFullInfo.getChipPath());
        queryChip.setChipInfo(chipInfo);
        return queryChip;
    }
    /**
     * 合成卡片
     * @param cardId 卡片ID
     * @param userId 用户ID
     * @return 返回合成的卡片信息
     */
    public SynCard syncard(String cardId,String userId){
        SynCard synCard=new SynCard();

        /*更新后的碎片信息*/
        StringBuilder upAfterChipinfo=new StringBuilder();

        /*更新后的卡片信息*/
        StringBuilder upAfterCardInfo=new StringBuilder();

        /*取出用户所有信息*/
        int userNumber=Integer.parseInt(userId);
        UserInfo userInfo=userInfoMapper.selectUserInfoByUserId(userNumber);

        //取出卡片对应的碎片信息
        int cardnumber=Integer.parseInt(cardId);
        String chipinfo=userInfo.getUserPiece();
        String[] chipinfoArray=chipinfo.split(",");

        //遍历卡片卡片数组修改卡片对应碎片数量
        /*设置标志位,减少遍历成本*/
        int state=0;//0-未遍历到卡片 1-正在遍历卡片 2-卡片遍历结束
        for(int i=0;i<chipinfoArray.length;i++){
            if (state==0||state==1){
                String[] info=chipinfoArray[i].split(":");
                if(Integer.parseInt(info[0])==cardnumber){
                    state=1;
                    Integer chipnum=Integer.parseInt(info[2])-1;
                    /*当卡片对应碎片数量为0时，对应碎片收集信息移出数组*/
                    if(chipnum==0){
                        chipinfoArray= (String[]) deldataFromArray(chipinfoArray,i);
                        i-=1;
                    }
                    else
                        chipinfoArray[i]=info[0]+":"+info[1]+":"+chipnum;

                }else if(state==1) state=2;
            }else break;
        }
        /*拼接碎片信息，用于更新数据库*/
        for(int w=0;w<chipinfoArray.length;w++){
            if(w!=chipinfoArray.length-1)
                upAfterChipinfo.append(chipinfoArray[w]).append(",");
            else
                upAfterChipinfo.append(chipinfoArray[w]);
        }

        //遍历卡片获取卡片信息
        String[] cardinfo=userInfo.getUserCard().split(",");
        for(int i=0;i<cardinfo.length;i++){
            String[] card=cardinfo[i].split(":");
            if(card[0].equals(cardId)){
                int cardNum=Integer.parseInt(card[1])+1;
                cardinfo[i]=card[0]+":"+cardNum;
                break;
            }
        }
        /*拼接卡片信息，用于更新数据库*/
        for(int i=0;i<cardinfo.length;i++){
            if(i!=cardinfo.length-1)
                upAfterCardInfo.append(cardinfo[i]).append(",");
            else
                upAfterCardInfo.append(cardinfo[i]);
        }

        /*更新数据库中用户拥有的碳积分*/
        //从数据库中取出卡片对应的碳积分
        int gradenum=cardInfoMapper.selectGradeBycardId(cardnumber);
        //取出用户对应的碳积分个数
        UserGrade userGrade=userInfoMapper.selectGradeByUserId(userNumber);
        //更新用户拥有的碳积分
        int allgradenew=userGrade.getUserGradeAll()+gradenum;
        int gradenew=userGrade.getUserGrade()+gradenum;
        userInfoMapper.updateGradeByNew(userNumber,allgradenew,gradenew);

        /*增加用户获取积分记录*/
        GradeList gradeList=new GradeList();
        gradeList.setUserId(userNumber);
        gradeList.setGradeNum(gradenum);
        gradeList.setGradeRemark("card");
        gradeListMapper.insertGradeListByRemark(gradeList);

        //更新碎片信息
        userInfoMapper.updateChipInfoByuserId(Integer.parseInt(userId),upAfterChipinfo.toString());

        //更新卡片信息
        userInfoMapper.updateCardInfoByUserId(Integer.parseInt(userId),upAfterCardInfo.toString());
        //查询对应卡片的路径信息以及等级信息
        synCard=cardInfoMapper.seleteCardBycardId(Integer.parseInt(cardId));
        return synCard;
    }

    /**
     * 跳转卡片页面请求
     * @param userId 用户ID
     * @return 用户收集碎片列表
     */
    public List<ChipNum> cardpage(String userId){
        List<ChipNum> list=new ArrayList<>();
        int userid=Integer.parseInt(userId);
        /*从数据库中查询用户碎片收集情况*/
        UserInfo userInfo=userInfoMapper.selectUserInfoByUserId(userid);
        String userpiece=userInfo.getUserPiece();
        String[] chipinfo=userpiece.split(",");

        /*设置哨兵，标定卡片ID的转换*/
        String guard=null;
        /*标定一张卡片的碎片数量*/
        int num=0;

        for(int i=0;i<chipinfo.length;i++){
            String[] chip=chipinfo[i].split(":");
            if(chip[0].equals(guard)){
                num+=Integer.parseInt(chip[2]);
            }else if(i!=0){
                ChipNum chipNum=new ChipNum();
                chipNum.setChipNum(num);
                /*存储上一个ID值*/
                i--;
                chipNum.setCardId(chipinfo[i].split(":")[0]);
                num=0;
                guard=chip[0];
                list.add(chipNum);
            }else{//特殊情况为当i为0，首个值时
                i--;
                guard=chip[0];
            }
        }
        /*存储最后一个卡片的信息*/
        ChipNum chipNum=new ChipNum();
        chipNum.setChipNum(num);
        chipNum.setCardId(guard);
        list.add(chipNum);
        return list;
    }

    /**
     * 查询一张卡片的具体碎片收集情况
     * @param userId
     * @param cardId
     * @return
     */
    public List<ChipCase> chipcollcase(String userId,String cardId){
        List<ChipCase> list=new ArrayList<>();
        /*取出用户所有信息*/
        int userNumber=Integer.parseInt(userId);
        UserInfo userInfo=userInfoMapper.selectUserInfoByUserId(userNumber);

        //取出卡片对应的碎片信息
        int cardnumber=Integer.parseInt(cardId);
        String chipinfo=userInfo.getUserPiece();
        String[] chipinfoArray=chipinfo.split(",");

        //标定是否遍历
        int state=0;
        for(int i=0;i<chipinfoArray.length;i++){
            if(state==0||state==1){
                String[] chip=chipinfoArray[i].split(":");
                if(chip[0].equals(cardId)){
                    state=1;
                    ChipCase chipCase=new ChipCase();
                    chipCase.setShipId(chip[1]);
                    chipCase.setShipNum(Integer.parseInt(chip[2]));
                    list.add(chipCase);
                }else if(state==1) state=2;
            }else break;
        }
        return list;
    }

    /**
     * 二分法查找数据
     * @param array 数组
     * @param left 数组左下标
     * @param right 数组右下标
     * @param real 真实值
     * @return 真实值所在数组中的下标 数组中无值时返回-1
     */
    public int binsearch(String[] array,int left,int right,String real){
        if(right>=left){
            int indexm=(right+left)/2;
            if(compareData(real,array[indexm])>0) binsearch(array,indexm,right,real);
            else if (compareData(real,array[indexm])<0) binsearch(array,left,indexm,real);
            else return indexm;
        }
        return -1;
    }

    /**
     * 删除数组中指定数据
     * @param array 数据
     * @param index 下标
     * @return 返回数组
     */
    public Object[] deldataFromArray(Object[] array,int index) {
        List<Object> list=Arrays.asList(array);
        List<Object> list1=new ArrayList<Object>(list);
        list1.remove(array[index]);
        Object[] arrays=new String[list1.size()];
        for(int i=0;i<arrays.length;i++){
            arrays[i]=list1.get(i);
        }
        return arrays;
    }

    /**
     * 里程数获得卡片等级
     * @param mileage 里程数
     * @return 卡片等级
     */
    public int cardHeightByMile(int mileage){
        if(mileage<10){
            return 1;
        }else if(mileage<20){
            return 2;
        }else return 3;
    }

    /**
     * 向数组中插入数据
     * @param array 原数组
     * @param data 数据
     * @return 新数组
     */
    public String[] insertData(String[] array,String data){
        for(int i=0;i<array.length;i++){
            if(compareData(data,array[i])>0){ //相比较于现阶段大
                if(i!=array.length-1&&compareData(data,array[i+1])<0){
                    //数组扩容
//                    array= (String[]) resizeArray(array,array.length+1);
                    for(int j=array.length-1;j>i+1;j--){
                        array[j]=array[j-1];
                    }
                    array[i+1]=data;
                    break;
                }else if(i==array.length-1){//当此时为最后一个值时，一定是插入操作
//                    array= (String[]) resizeArray(array,array.length+1);
                    for(int j=array.length-1;j>i+1;j--){
                        array[j]=array[j-1];
                    }
                    array[i+1]=data;
                    break;
                }else continue;//此时说明下一个值与当前值相同

            }else if(data.equals(array[i])){//此时是获取碎片与其数据库中相同，执行更新操作
                String[] tempdata=data.split(":");
                int chipnum=Integer.parseInt(tempdata[2])+1;
                array[i]=tempdata[0]+":"+tempdata[1]+":"+chipnum;
                break;
            }else if(i==0&&compareData(data,array[i])>0){
//                array= (String[]) resizeArray(array,array.length+1);
                for(int j=array.length-1;j>i+1;j--){
                    array[j]=array[j-1];
                }
                array[i+1]=data;
                break;
            }
        }
        return array;
    }

    /**
     * 判断是否有合成卡片的可能
     * @param array 碎片数据
     * @param cardId 碎片ID
     * @return 返回数据
     */
    public boolean isNewCard(String[] array,String cardId){
        int chipnum=0;//记录碎片个数
        for(int i=0;i<array.length;i++){
            String data=array[i].split(":")[0];
            if(data.equals(cardId))
                chipnum++;
            if (chipnum==5) break;
        }
        if(chipnum==5) return true;
        return false;
    }

    /**
     * 数组扩容
     * @param oldArray 原数组
     * @param newSize 新的长度
     * @return 新数组
     */
    private static Object resizeArray(Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(elementType,
                newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0)
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        return newArray;
    }

    /**
     * 比较两个数的大小
     * @param one 卡片ID：碎片ID：数量
     * @param two 卡片ID：碎片ID：数量
     * @return 1--one>two -1--one<two 0--onw=two
     */
    public int compareData(String one,String two){
        String[] onearray=one.split(":");
        String[] twoarray=two.split(":");
        if(Integer.parseInt(onearray[0])>Integer.parseInt(twoarray[0])){
            return 1;
        }else if(Integer.parseInt(onearray[0])==Integer.parseInt(twoarray[0])){
            if(Integer.parseInt(onearray[1])>Integer.parseInt(twoarray[1])) return 1;
            else if(Integer.parseInt(onearray[1])<Integer.parseInt(twoarray[1])) return -1;
            else return 0;
        }else return -1;
    }

}
