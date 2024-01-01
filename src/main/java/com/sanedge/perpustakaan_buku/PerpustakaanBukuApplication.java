package com.sanedge.perpustakaan_buku;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import com.sanedge.perpustakaan_buku.enums.ERole;
import com.sanedge.perpustakaan_buku.models.Role;
import com.sanedge.perpustakaan_buku.repository.RoleRepository;

@SpringBootApplication
@EnableKafkaStreams
public class PerpustakaanBukuApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(PerpustakaanBukuApplication.class, args);

		RoleRepository roleRepository = context.getBean(RoleRepository.class);

		List<ERole> roles = Arrays.asList(ERole.ROLE_USER, ERole.ROLE_MODERATOR, ERole.ROLE_ADMIN);

		roles.forEach(role -> {
			if (!roleRepository.findByName(role != null ? role : ERole.ROLE_USER).isPresent()) {
				Role newRole = new Role();
				newRole.setName(role);
				roleRepository.save(newRole);
				System.out.println("Created role: " + role);
			} else {
				System.out.println("Role " + role + " already exists.");
			}
		});
	}

}
