#ifdef GL_ES
    #define LOWP lowp
    precision mediump float;
#else
    #define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
varying float alp;
uniform sampler2D u_texture;
void main(){
    //как и в стандартном шейдере получаем итоговый цвет пикселя
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
    gl_FragColor.rgb = gl_FragColor.rgb * alp;
}