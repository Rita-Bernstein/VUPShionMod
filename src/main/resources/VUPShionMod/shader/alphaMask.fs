#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform sampler2D u_mask;
void main()
{
    vec4 texColor = texture2D(u_texture, v_texCoords);
    vec4 maskColor = texture2D(u_mask, v_texCoords);
    gl_FragColor = v_color * texColor * vec4(1, 1, 1, maskColor.r);
}
