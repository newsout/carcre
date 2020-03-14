package com.sout.carcre.service;

import com.sout.carcre.controller.bean.CardPage;
import com.sout.carcre.controller.bean.ChipCollCase;
import com.sout.carcre.controller.bean.QueryChip;
import com.sout.carcre.controller.bean.SynCard;
import com.sout.carcre.controller.bean.beanson.*;
import com.sout.carcre.mapper.*;
import com.sout.carcre.mapper.bean.GradeList;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.service.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CardService {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    CardInfoMapper cardInfoMapper;
    @Autowired
    GradeListMapper gradeListMapper;
    @Autowired
    CollListMapper collListMapper;
    @Autowired
    RankWeeklyMapper rankWeeklyMapper;

    /**
     * 随机获取碎片
     * @param userId
     * @param mileage
     * @return
     */
    public QueryChip querychip(String userId,int mileage){

        /*返回数据*/
        QueryChip queryChip=new QueryChip();

        //获得里程数对应的卡片等级
        int height=cardHeightByMile(mileage);

        //根据等级获得碎片信息
        List<ChipFromCard> chiplist=cardInfoMapper.seleteChipByheight(height);

        //获得随机数 randNum
        int sumnum=chiplist.size()*9;
        Random random=new Random();
        int randNum=random.nextInt(sumnum)+1;

        //根据随机数获得具体获得的碎片信息
        int listindex=(randNum-1)/9;
        int interindex=(randNum-1)%9;

        /*获取的碎片详细信息*/
        ChipFullInfo chipFullInfo=new ChipFullInfo();
        ChipFromCard chipFromCard=chiplist.get(listindex);
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

        /*合并卡片数据*/
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<newchipinfo.length;i++){
            stringBuilder.append(newchipinfo[i]).append(",");
        }

        /*更新数据库信息*/
        userInfoMapper.updateChipInfoByuserId(Integer.parseInt(userId),stringBuilder.toString());

        /*向数据库中插入用户获取随机碎片记录*/
        String chipid=chipFullInfo.getCardId()+":"+chipFullInfo.getChipId();
        collListMapper.insertChipLogByCollList(Integer.parseInt(userId),chipid);

        /*更新周排行榜中的碎片数量*/
        //查询原有的碎片数量
        int ChipNum=rankWeeklyMapper.seleteChipNumByUserId(Integer.parseInt(userId))+1;
        //更新碎片信息
        rankWeeklyMapper.updateChipNumByUserId(Integer.parseInt(userId),ChipNum);

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
    public boolean syncard(String cardId,String userId){
//        SynCard synCard=new SynCard();

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

        //记录修改碎片信息的个数
        int newchipnumber=0;

        //获取碎片对应的要合成的数量
        int cardWillSyn=ChipNumMin(chipinfoArray,cardId);
        System.out.println("合成的卡片数量"+cardWillSyn);

        //遍历卡片数组修改卡片对应碎片数量
        /*设置标志位,减少遍历成本*/
        int state=0;//0-未遍历到卡片 1-正在遍历卡片 2-卡片遍历结束
        for(int i=0;i<chipinfoArray.length;i++){
            if (state==0||state==1){
                String[] info=chipinfoArray[i].split(":");
                if(Integer.parseInt(info[0])==cardnumber){
                    newchipnumber++;//增加碎片修改个数
                    state=1;
                    Integer chipnum=Integer.parseInt(info[2])-cardWillSyn;
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
        if(newchipnumber!=9){//5为碎片的总个数，判断此时是否可以合成
            return false;
        }
        /*拼接碎片信息，用于更新数据库*/
        for(int w=0;w<chipinfoArray.length;w++){
            upAfterChipinfo.append(chipinfoArray[w]).append(",");
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
            upAfterCardInfo.append(cardinfo[i]).append(",");
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
        GradeListInfo gradeList=new GradeListInfo();
        gradeList.setUserId(userNumber);
        gradeList.setGrade(gradenum);
        gradeList.setGradeRemark("card");
        gradeListMapper.insertGradeListByRemark(gradeList);

        //更新碎片信息
        userInfoMapper.updateChipInfoByuserId(Integer.parseInt(userId),upAfterChipinfo.toString());

        //更新卡片信息
        userInfoMapper.updateCardInfoByUserId(Integer.parseInt(userId),upAfterCardInfo.toString());

        //更新用户周排行榜数据
        int weekGradeNum=rankWeeklyMapper.selectGradeNumByUserId(Integer.parseInt(userId))+gradenum;
        rankWeeklyMapper.updateGradeNumByUserID(Integer.parseInt(userId),weekGradeNum);
//
//        //查询对应卡片的路径信息以及等级信息
//        synCard=cardInfoMapper.seleteCardBycardId(Integer.parseInt(cardId));
        return true;
    }

    /**
     * 跳转卡片页面请求
     * @param userId 用户ID
     * @return 用户收集碎片列表
     */
    public CardPage cardpage(String userId){
        CardPage cardPage=new CardPage();
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

        /*标定一张卡片中各碎片的收集情况*/
        String[] cardNum=new String[9];
        int chipindex=0;//表示当前在卡片收集情况数组中的位置

        for(int i=0;i<chipinfo.length;i++){
            String[] chip=chipinfo[i].split(":");
            if(chip[0].equals(guard)){
                num+=Integer.parseInt(chip[2]);
                cardNum[chipindex++]=chip[2];//记录卡片中各碎片的收集情况
            }else if(i!=0){
                boolean state=true;//标定是否可以将此卡片返回给用户
                //判断当前卡片是否超过限定卡片日期,查询卡片限定日期
                int cardId= Integer.parseInt(chipinfo[i].split(":")[0]);
                String cardLimit=cardInfoMapper.selectCardLimitByCardId(cardId);
                if(!"0".equals(cardLimit)){//为限定卡片
                    if(!compareTime(cardLimit)) state=false;//卡片过期
                }
                if(state){
                    //待合成的卡片个数
                    int realCardnum=realCardNum(cardNum);
                    //剔除卡片合成所有碎片后剩余的碎片数量
                    int realchipNum=notRepeatChip(cardNum,realCardnum);
                    ChipNum chipNum=new ChipNum();
                    chipNum.setChipNum(realchipNum);
                    chipNum.setCardNum(realCardnum);
                    chipNum.setCardLimit(cardLimit);
                    chipNum.setCardId(chipinfo[i].split(":")[0]);
                    list.add(chipNum);
                }
                /*存储上一个ID值*/
                i--;
                num=0;
                chipindex=0;//开启新的卡片时，将记录卡片中各碎片数量的编号置为从头开始
                guard=chip[0];

            }else{//特殊情况为当i为0，首个值时
                i--;
                guard=chip[0];
            }
        }
        /*存储最后一个卡片的信息*/
        ChipNum chipNum=new ChipNum();
        int cardId= Integer.parseInt(chipinfo[chipinfo.length-1].split(":")[0]);
        boolean state=true;
        String cardLimit=cardInfoMapper.selectCardLimitByCardId(cardId);
        if(!"0".equals(cardLimit)){//为限定卡片
            if(!compareTime(cardLimit)) state=false;//卡片过期
        }
        if(state){
            int realCardnum=realCardNum(cardNum);
            int realchipNum=notRepeatChip(cardNum,realCardnum);
            chipNum.setChipNum(realchipNum);
            chipNum.setCardNum(realCardnum);
            chipNum.setCardId(String.valueOf(cardId));
            list.add(chipNum);
        }


        /*存储用户数据*/
        UserForCard userForCard=new UserForCard();
        //获取用户已经收集的卡片数量
        int cardAllnum=cardAllnum(userInfo.getUserCard());
        userForCard.setNickName(userInfo.getNickname());
        userForCard.setUserImagePath(userInfo.getUserImagePath());
        userForCard.setCardAllNum(cardAllnum);
        cardPage.setUserForCard(userForCard);
        cardPage.setChipNumList(list);
        return cardPage;
    }

    /**
     * 查询一张卡片的具体碎片收集情况
     * @param userId
     * @param cardId
     * @return
     */
    public ChipCollCase chipcollcase(String userId, String cardId){
        ChipCollCase chipCollCase=new ChipCollCase();
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
        chipCollCase.setChipCaseList(list);
        /*获取用户信息*/
        CardRoughInfo cardRoughInfo=new CardRoughInfo();
        int cardAllnum=cardNumBycardId(userInfo.getUserCard(),cardId);
        cardRoughInfo.setCardNum(cardAllnum);
        //根据卡片ID查询卡片具体信息
        CardFewInfo cardFewInfo = cardInfoMapper.selectCardFewInfoByCardId(Integer.parseInt(cardId));
        cardRoughInfo.setCardDescribe(cardFewInfo.getCardDescribe());
        cardRoughInfo.setCardHeight(cardFewInfo.getCardHeight());
        chipCollCase.setCardRoughInfo(cardRoughInfo);

        /*判断用户是否有卡片合成*/
        boolean cardIsSyn=(list.size()==9);
        chipCollCase.setChipIsSyn(cardIsSyn);
        return chipCollCase;
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
                    if(array[array.length-1]!=null|| !array[array.length - 1].equals(""))
                        array= (String[]) resizeArray(array,array.length+1);
                    for(int j=array.length-1;j>i+1;j--){
                        array[j]=array[j-1];
                    }
                    //插入到当前值的下一个值
                    array[i+1]=data;
                    break;
                }else if(i==array.length-1){//当此时为最后一个值时，一定是插入操作

                    if(array[array.length-1]!=null|| !array[array.length - 1].equals(""))
                        array= (String[]) resizeArray(array,array.length+1);

//                    array= (String[]) resizeArray(array,array.length+1);
                    for(int j=array.length-1;j>i+1;j--){
                        array[j]=array[j-1];
                    }
                    //插入到当前值的后一个值
                    array[i+1]=data;
                    break;
                }else continue;//此时说明下一个值与当前值相同

            }else if(compareData(data,array[i])==0){//此时是获取碎片与其数据库中相同，执行更新操作
                String[] tempdata=array[i].split(":");
                int chipnum=Integer.parseInt(tempdata[2])+1;
                array[i]=tempdata[0]+":"+tempdata[1]+":"+chipnum;
                break;

            }else if(i==0&&compareData(data,array[i])<0){
                //如果数组最后一个数据不为null，则扩充数组
                if(array[array.length-1]!=null|| !array[array.length - 1].equals(""))
                    array= (String[]) resizeArray(array,array.length+1);
                for(int j=array.length-1;j>i;j--){
                    array[j]=array[j-1];
                }
                array[i]=data;
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
            if (chipnum==9) break;
        }
        if(chipnum==9) return true;
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

    /**
     * 获取用户带合成的卡片数量
     * @param chipNumArray 碎片收集情况数组
     * @return
     */
    public int realCardNum(String[] chipNumArray){
        int num=Integer.parseInt(chipNumArray[0]);
        if(chipNumArray.length!=9) return 0;
        for(int i=0;i<chipNumArray.length;i++){
            if(chipNumArray[i]==null|| chipNumArray[i].equals("")) return 0;
            else if(Integer.parseInt(chipNumArray[i])<num) {
                num=Integer.parseInt(chipNumArray[i]);
            }
        }
        return num;
    }

    /**
     *
     * 返回用户已经收集的卡片总数
     * @param userCard
     * @return
     */
    public int cardAllnum(String userCard){
        int num=0;//收集卡片的总数量
        String[] cardArray=userCard.split(",");
        for(int i=0;i<cardArray.length;i++){
            num+=Integer.parseInt(cardArray[i].split(":")[1]);
        }
        return num;
    }

    /**
     * 返回用户收集指定卡片的个数
     * @param userCard 用户收集所有卡片信息
     * @param cardId 要获得信息的卡片ID
     * @return
     */
    public int cardNumBycardId(String userCard,String cardId){
        int num=0;
        String[] cardArray=userCard.split(",");
        for(int i=0;i<cardArray.length;i++){
            if(cardId!=null&&cardId.equals(cardArray[i].split(":")[0]))
                num+=Integer.parseInt(cardArray[i].split(":")[1]);
        }
        return num;
    }

    /**
     * 获取用户不重复的卡片数量
     * @param chipNum
     * @return
     */
    public int notRepeatChip(String[] chipNum,int cardNum){
        int number=0;
        for(int i=0;i<chipNum.length;i++){
            if(chipNum[i]==null|| chipNum[i].equals("")) return number;
            if(Integer.parseInt(chipNum[i])>cardNum) number++;
            chipNum[i]=null;
        }
        return number;
    }

    /**
     * 返回一张卡片中对应的碎片数组中碎片数量的最小值
     * @param chipInfo
     * @return
     */
    public int ChipNumMin(String[] chipInfo,String cardId){
        int minNum=Integer.parseInt(chipInfo[0].split(":")[2]);
        if(chipInfo!=null){
            for(int i=0;i<chipInfo.length;i++){
                String chipsplit=chipInfo[i].split(":")[2];
                if(cardId.equals(chipInfo[i].split(":")[0])&&minNum>Integer.parseInt(chipsplit)) minNum=Integer.parseInt(chipsplit);
            }
        }
        return minNum;
    }

    /**
     * 判断时间是否超过当前时间
     * @param time
     * @return
     */
    public boolean compareTime(String time){
        //获取当前时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String presentTime=simpleDateFormat.format(new Date());
        String[] limitTime=time.split("-");
        String[] nowTime=presentTime.split("-");
        for(int i=0;i<limitTime.length;i++){
            int nowTimeData=Integer.parseInt(nowTime[i]);
            int limitTimeData=Integer.parseInt(limitTime[i]);
            if(nowTimeData<limitTimeData) return true;
        }
        return false;
    }
}
