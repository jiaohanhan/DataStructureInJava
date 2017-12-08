package linear_list;

import java.util.Arrays;

public class SequenceList<T> {

    private final int DEFAULT_SIZE = 16;

    //保存数组长度
    private int capacity;
    //定义一个数组用于保存顺序线性表的元素
    private Object[] elementData;
    //保存顺序表中元素的当前个数
    private int size = 0;

    //以默认数组长度创建空顺序线性表
    public SequenceList(){
        capacity = DEFAULT_SIZE;
        elementData = new Object[capacity];
    }

    //以一个初始化元素创建顺序线性表
    public SequenceList(T element){
        this();
        elementData[0] = element;
        size++;
    }

    //以指定长度的数组来创建顺序线性表，指定顺序线性表中第一个元素，指定顺序线性表底层数组的长度
    public SequenceList(T element, int initSize){
        capacity = 1;
        while (capacity < initSize)
            capacity <<= 1;
        elementData = new Object[capacity];
        elementData[0] = element;
        size++;
    }

    //获取顺序线性表的大小
    public int length(){
        return size;
    }

    //获取顺序线性表中索引为i处的元素
    public T get(int i){
        if(i < 0 || i > size - 1)
            throw new IndexOutOfBoundsException("越界");
        return (T) elementData[i];
    }

    //查找顺序线性表中指定元素的索引
    public int locate(T element){
        for (int i = 0; i < size; i++) {
            if(elementData[i].equals(element))
                return i;
        }
        return -1;
    }

    //向顺序线性表的指定位置插入一个元素
    public void insert(T element, int index){
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException("越界");
        ensureCapacity(size + 1);
        //将指定索引处之后的所有元素向后移动一格
        System.arraycopy(elementData, index,elementData,index+1,size-index);
        elementData[index] = element;
        size++;
    }

    //性能很差劲
    private void ensureCapacity(int minCapacity) {
        if(minCapacity > capacity){
            while (capacity < minCapacity)
                capacity <<= 1;
            elementData = Arrays.copyOf(elementData, capacity);
        }
    }

    //在线性顺序表的开始添加一个元素
    public void add(T element){
        insert(element,size);
    }

    //删除顺序线性表中指定索引处的元素
    public T delete(int index){
        if(index < 0 || index > size- 1){
            throw new IndexOutOfBoundsException("越界");
        }
        T oldValue = (T) elementData[index];
        int numMoved = size - index + 1;
        if(numMoved > 0){
            System.arraycopy(elementData, index+1,elementData,index,numMoved);
        }
        //清空最后一个元素
        elementData[--size] = null;
        return oldValue;
    }

    //删除顺序线性表中最后一个元素
    public T remove(){
        return delete(size - 1);
    }

    //判断顺序线性表是否为空表
    public boolean empty(){
        return size == 0;
    }

    //清空线性表
    public void clear(){
        Arrays.fill(elementData, null);
        size = 0;
    }
    
    public String toString() {
        if(size == 0)
            return "[]";
        else{
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < size; i++) {
                sb.append(elementData[i].toString() + ", ");
            }
            int len = sb.length();
            return sb.delete(len - 2, len).append("]").toString();
        }
    }
}
