attribute vec4 a_position; //позиция вершины
attribute vec4 a_color; //цвет вершины
attribute vec4 alpha_color; //цвет вершины
attribute vec2 a_texCoord0; //координаты текстуры
uniform mat4 u_projTrans;  //матрица, которая содержим данные для преобразования проекции и вида
varying vec4 v_color;  //цвет который будет передан в фрагментный шейдер
varying vec2 v_texCoords;  //координаты текстуры
varying float alp;
void main(){
    v_color=a_color;
    alp = alpha_color.a * (255.0/254.0);
    v_color.a = v_color.a * (255.0/254.0);
    v_texCoords = a_texCoord0;
    gl_Position =  u_projTrans * a_position;
}