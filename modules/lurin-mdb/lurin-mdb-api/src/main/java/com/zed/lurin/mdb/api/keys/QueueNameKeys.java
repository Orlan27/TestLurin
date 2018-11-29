package com.zed.lurin.mdb.api.keys;

public enum QueueNameKeys {
    VALIDATION_QUEUE_NAME{
        @Override
        public String toString() {
            return "queue/validationCommandCasQueue";
        }
    },
    SEND_COMMAND_QUEUE_NAME{
        @Override
        public String toString() {
            return "queue/sendCommandCasQueue";
        }
    }
}
