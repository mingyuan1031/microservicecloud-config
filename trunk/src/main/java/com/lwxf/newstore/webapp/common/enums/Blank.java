package com.lwxf.newstore.webapp.common.enums;

/**
 * 区分绑定店主和添加店员的事件id
 */
public enum Blank {

        BINDSHOPKEEPER(0), 		// 绑定店主
        ADDCLERK(1);	//添加店员

        private int value;
    Blank(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }

        public static boolean validValue(int value){
            Blank[] arr = values();
            for (int m=0,len = arr.length;m<len;m++){
                if(arr[m].value == value){
                    return true;
                }
            }
            return false;
        }

}
