package com.edunexus.backend.admin;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.edunexus.backend.login.Login;
import com.edunexus.backend.login.LoginRepository;
import com.edunexus.backend.teacher.Teacher;
import com.edunexus.backend.teacher.TeacherRepository;

class AdminSeederTest {

    @Test
    void seedAdminsCreatesFallbackDemoLogins() throws Exception {
        LoginRepository loginRepo = mock(LoginRepository.class);
        TeacherRepository teacherRepo = mock(TeacherRepository.class);
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        when(loginRepo.existsById(any(String.class))).thenReturn(false);
        when(teacherRepo.existsById(any(String.class))).thenReturn(false);
        when(loginRepo.save(any(Login.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(teacherRepo.save(any(Teacher.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AdminSeeder seeder = new AdminSeeder();
        CommandLineRunner runner = seeder.seedAdmins(loginRepo, teacherRepo, encoder);

        runner.run();

        verify(loginRepo, atLeast(1)).save(argThat(login ->
            "admin01".equals(login.getEdu_id()) || "teacher01".equals(login.getEdu_id()) || "student01".equals(login.getEdu_id())
        ));
    }
}
