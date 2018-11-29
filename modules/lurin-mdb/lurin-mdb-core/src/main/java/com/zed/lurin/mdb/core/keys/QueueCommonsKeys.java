package com.zed.lurin.mdb.core.keys;

public enum QueueCommonsKeys {
    QUEUE_CONNECTION_FACTORY_NAME{
        @Override
        public String toString() {
            return "java:/JmsXA";
        }
    }
}
