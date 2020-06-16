<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- namespace表示命名空间 -->
<mapper namespace="com.mapper.AnswerMapper">

    <insert id="insert" parameterType="com.pojo.Answer">
        insert into Answer (question_id,answer) values(#{question_id},#{answer})
    </insert>
    <update id="updateById" parameterType="integer">
        update Answer
        <set>
            <if test="answer!=null and answer!=''">answer = #{answer},</if>
        </set>
        where question_id = #{question_id}
    </update>
    <delete id="deleteById" parameterType="integer">
        delete from Answer where question_id = #{question_id}
    </delete>
    <select id="findById" parameterType="integer" resultType="com.pojo.Answer">
        select * from Answer where question_id = #{question_id}
    </select>
</mapper>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 