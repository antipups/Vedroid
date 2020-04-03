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
        <div class="mainDiv">
            <button onClick="street_mode()" >Улица</button>
            <button onClick="listen_audio()" >Прослушивание музыки</button>
            <button onClick="all_off()" >Отключение всего</button>
        </div>
        <script type="text/javascript">${script}</script>
    </body>
    </html>
""".trimIndent()


