package UI;

class Utilities {//0 合法 1 不是数字 2 超过范围
    static int judgeInput(String input){
        if(input.isEmpty())
            return -1;
        for(int i=0;i<input.length();i++){
            if(!Character.isDigit(input.charAt(i)))
                return 1;
        }
        int weight=Integer.parseInt(input);
        if(weight<0||weight>100)
            return 2;
        return 0;
    }
}
