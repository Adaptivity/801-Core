package core.common.integration.othermods.shaders;

import static org.lwjgl.opengl.ARBShaderObjects.glGetUniformLocationARB;
import static org.lwjgl.opengl.ARBShaderObjects.glUniform1fARB;
import static org.lwjgl.opengl.ARBShaderObjects.glUniform1iARB;
import static org.lwjgl.opengl.ARBShaderObjects.glUniform2fARB;
import static org.lwjgl.opengl.ARBShaderObjects.glUniform3fARB;

/**
 * Copyright (c) 2013, ScreamTech & Zerrox96
 */
public class ShaderHelper {

	public Shader shader;
	public ShaderLoader shaderloader = new ShaderLoader();
	public int currentProgram;
	public float alphaValue = 1.0f;

	public ShaderHelper(Shader shader)
	{
		this.shader = shader;
		currentProgram = shader.shaderProgramm;
	}

	public void setProgramUniform1i(String name, int x)
	{
		if (currentProgram == 0)
		{
			return;
		}
		int uniform = glGetUniformLocationARB(currentProgram, name);
		glUniform1iARB(uniform, x);
	}

	public void setProgramUniform1f( String name, float x)
	{
		if (currentProgram == 0)
		{
			return;
		}
		int uniform = glGetUniformLocationARB(currentProgram, name);
		glUniform1fARB(uniform, x);
	}

	public void setProgramUniform2f( String name, float x, float y)
	{
		if (currentProgram == 0)
		{
			return;
		}
		int uniform = glGetUniformLocationARB(currentProgram, name);
		glUniform2fARB(uniform, x, y);
	}

	public void setProgramUniform3f(String name, float x, float y, float z)
	{
		if (currentProgram == 0)
		{
			return;
		}
		int uniform = glGetUniformLocationARB(currentProgram, name);
		glUniform3fARB(uniform, x, y, z);
	}

	public void reloadShaderProgram(int shaderprogram, String vert, String frag)
	{
		shaderprogram = ShaderLoader.loadShaderPair(vert, frag);
	}


}
