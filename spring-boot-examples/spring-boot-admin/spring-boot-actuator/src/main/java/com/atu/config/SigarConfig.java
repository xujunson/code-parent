package com.atu.config;

import lombok.extern.slf4j.Slf4j;
import org.hyperic.jni.ArchNotSupportedException;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;

/**
 * @Author: Tom
 * @Date: 2021/5/31 5:56 下午
 * @Description:
 */
@Slf4j
@Configuration
public class SigarConfig {

    static {
        try {
            initSigar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *     初始化sigar的配置文件
     */
    public static void initSigar() throws IOException {
        log.info("==initSigar==");
        SigarLoader loader = new SigarLoader(Sigar.class);
        String lib = null;

        try {
            lib = loader.getLibraryName();

        } catch (ArchNotSupportedException var7) {
        }
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/sigar.so/" + lib);
        if (resource.exists()) {
            log.info("==exists==");
            InputStream is = resource.getInputStream();
            File tempDir = new File("./log");
            if (!tempDir.exists()){
                tempDir.mkdirs();
            }
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(tempDir, lib), false));
            int lentgh = 0;
            while ((lentgh = is.read()) != -1){
                os.write(lentgh);
            }

            is.close();
            os.close();
            System.setProperty("org.hyperic.sigar.path", tempDir.getCanonicalPath());
        }

    }
}
