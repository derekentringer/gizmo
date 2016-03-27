#ifdef GL_ES
    precision mediump float;
#endif

uniform sampler2D u_texture;
uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoords;

void main() {
    vec3 color = texture2D(u_texture, v_texCoords).rgb;

    float white = (color.r + color.g + color.b);
    vec3 whiteout = vec3(white);

    float black = (color.r - color.g - color.b);
    vec3 blackout = vec3(black);

    gl_FragColor = vec4(whiteout, 1.0);
}