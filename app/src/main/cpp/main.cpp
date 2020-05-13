#include "jni.h"
#include "android/log.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <termios.h>
#include <unistd.h>

#define LOG_TAG "System.out"

// debug log
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
// info log
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

static bool config_uart(int uart_fd, int speed) {
    static  struct termios termold, termnew;

    if(tcgetattr(uart_fd, &termold) != 0)
    {
        return false;
    }
    bzero(&termnew, sizeof(termnew));
    termnew.c_iflag &= ~(ICRNL | IGNCR) ;
    termnew.c_cflag |= CLOCAL | CREAD;   //CLOCAL:忽略modem控制线  CREAD：打开接受者
    termnew.c_cflag &= ~CSIZE;
    termnew.c_cflag |= CS8;
    termnew.c_cflag &= ~PARENB;

    switch(speed)
    {
        case 2400:
            cfsetispeed(&termnew, B2400);
            cfsetospeed(&termnew, B2400);
            break;
        case 4800:
            cfsetispeed(&termnew, B4800);
            cfsetospeed(&termnew, B4800);
            break;
        case 9600:
            cfsetispeed(&termnew, B9600);
            cfsetospeed(&termnew, B9600);
            break;
        case 115200:
            cfsetispeed(&termnew, B115200);
            cfsetospeed(&termnew, B115200);
            break;
        case 460800:
            cfsetispeed(&termnew, B460800);
            cfsetospeed(&termnew, B460800);
            break;
        default:
            cfsetispeed(&termnew, B115200);
            cfsetospeed(&termnew, B115200);
            break;
    }
    termnew.c_cflag &=  ~CSTOPB;
    termnew.c_cc[VTIME]  = 1;    //VTIME:非cannoical模式读时的延时，以十分之一秒位单位
    termnew.c_cc[VMIN] = 0;       //VMIN:非canonical模式读到最小字符数
    tcflush(uart_fd, TCIFLUSH);
    // 改变在所有写入 fd 引用的对象的输出都被传输后生效，所有已接受但未读入的输入都在改变发生前丢弃。
    if((tcsetattr(uart_fd, TCSANOW,&termnew)) != 0)   //TCSANOW:改变立即发生
    {
        return false;
    }
    return true;
}

static int safety_read(int fd, char *read_data, unsigned int data_len) {
    unsigned int len = 0;
    //char byte_data;

    while(data_len) {
        //bzero(&byte_data, 1);
        len += read(fd, read_data + len, 1);
        data_len--;
    }
    LOGI("%s", read_data);
    LOGI("%d", len);

    return len;
}

#define WRITE_DATA_LEN 10
/*
 * COM1  /dev/ttyS0   COM2  /dev/ttyS1
 * COM3  /dev/ttyS3   COM4  /dev/ttyS4
 */
static int test_com(char *com_name_a, char *com_name_b) {

    char read_data[10], *write_char = "abcd01234\0";
    int fd_a, fd_b, read_len = 0,  strcmp_result = -1;

    bzero(read_data, 10);

    fd_a = open(com_name_a, O_RDWR | O_NOCTTY);
    if (fd_a < 0) {
        goto ERR0;
    }

    fd_b = open(com_name_b, O_RDWR | O_NOCTTY);
    if (fd_b < 0) {
        goto ERR1;
    }

    config_uart(fd_a, 115200);
    config_uart(fd_b, 115200);

    write(fd_a, write_char, 10);
    usleep(100 * 1000);
    read_len = safety_read(fd_b, read_data, WRITE_DATA_LEN);

    if (read_len != WRITE_DATA_LEN) {
        goto ERR2;
    }

    strcmp_result = strcmp(read_data, write_char);
    if (strcmp_result == 0) {
        close(fd_b);
        close(fd_a);
        return 1;
    } else {
        goto ERR2;
    }

ERR2:
    close(fd_b);
ERR1:
    close(fd_a);
ERR0:
    return 0;
}

#define  COM1  "/dev/ttyS0"
#define  COM2  "/dev/ttyS1"
#define  COM3  "/dev/ttyS3"
#define  COM4  "/dev/ttyS4"

extern "C"
JNIEXPORT jint JNICALL
        Java_com_example_cmi_1at154_1test_MainActivity_ReturnCOM1Result
        (JNIEnv *env, jobject thiz) {
    return test_com(COM1, COM2);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_cmi_1at154_1test_MainActivity_ReturnCOM2Result
        (JNIEnv *env, jobject thiz) {
    return test_com(COM2, COM1);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_cmi_1at154_1test_MainActivity_ReturnCOM3Result
        (JNIEnv *env, jobject thiz) {
    return test_com(COM3, COM4);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_cmi_1at154_1test_MainActivity_ReturnCOM4Result
        (JNIEnv *env, jobject thiz) {
    return test_com(COM4, COM3);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_cmi_1at154_1test_MainActivity_ReturnUSBResult
        (JNIEnv *env, jobject thiz) {
    return 1;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_cmi_1at154_1test_MainActivity_ReturnGPSResult
        (JNIEnv *env, jobject thiz) {
    return 1;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_cmi_1at154_1test_MainActivity_ReturnWIFIResult
        (JNIEnv *env, jobject thiz) {
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_cmi_1at154_1test_MainActivity_Return4GResult
        (JNIEnv *env, jobject thiz) {
    return 0;
}
