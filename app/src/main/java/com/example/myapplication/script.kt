package com.example.myapplication

val script =
"""
   function street_mode() 
    {
        Android.showToast("Уличный режим");
        Android.brightness_half();
    } 
    function listen_audio()
    {
        Android.showToast("Режим прослушивания музыки");
        Android.bluetoothOn();
    }
    function all_off()
    {
        Android.showToast("Все адаптеры отключены");
        Android.bluetoothOff();
        Android.wifiOff();
    }
""".trimIndent()