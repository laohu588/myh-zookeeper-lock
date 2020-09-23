# myh-zookeeper-lock
基于spring-boot、zookeeper实现分布式锁。


1、解决mysql、redis死锁的问题;<br>
2、可以优雅的实现分布式阻塞式加锁;


具体使用业务示例代码：

    
        private Lock lock = new ZookeeperDistruteLock("192.168.1.1:2181", "myh_lock001");
        
        @SuppressWarnings("static-access")
        @RequestMapping("/lock")
        public String lock() {
    
            try {
    
                lock.lock();
                System.out.println("1、加锁成功");
                
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2、处理完成业务");
    
            } finally {
                lock.unlock();
                System.out.println("3、释放锁");
            }
            return "东虎霸业>>>200";
        
        }
    
