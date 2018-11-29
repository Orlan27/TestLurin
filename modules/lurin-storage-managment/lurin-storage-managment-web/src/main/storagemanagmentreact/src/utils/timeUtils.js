export default function convertTimePickerValueToString(time) {
    let hour = time/3600;
    hour = hour - (hour%1);
    let hourString = (hour.toString().length) > 1 ? hour.toString() : "0" + hour;
    let minute = time%3600;
    minute = minute/60;
    let minuteString =  (minute.toString().length) > 1 ? minute.toString() : "0" + minute;
    return hourString + ":" + minuteString;
}