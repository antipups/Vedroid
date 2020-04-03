package com.example.myapplication
import com.example.myapplication.script
import com.example.myapplication.style

val html_code =
"""
    <!doctype html>
    <head>
        <style>${style}</style>
    </head>
    <body>
        <button value="Say hello" onClick="showAndroidToast('Приложение запущено!')" >Привет</button>
    
        <script type="text/javascript">${script}</script>
    </body>
    </html>
""".trimIndent()


