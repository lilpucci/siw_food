package it.uniroma3.siwfood.siw_food.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class AuthConfiguration {
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    private void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
            .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabilita CSRF se necessario, o configuralo come richiesto
                .cors(cors -> cors.disable()) // Disabilita CORS se necessario, o configuralo come richiesto
                // AUTORIZZAZIONE: qui definiamo chi può accedere a cosa
                .authorizeHttpRequests( authorize -> authorize
                        // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
                        .requestMatchers(HttpMethod.GET, "/", "/login", "/register", "/cuochi/**", "/ricette/**" , "/css/**", "/image/**", "/cerca/**").permitAll()  //tolto le icon
                        // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register
                        .requestMatchers(HttpMethod.POST, "/", "/login", "/register", "/cuochi/**", "/ricette/**", "/css/**", "/image/**", "/cerca/**").permitAll()
                        //solo admin e utenti registrati possono aggiungere-cancellare-modificare risorse  (controlla che chi non è admin lavori per le sue risorse)
                        
                        /* se non sei loggato non mostrate le ancore,
                           se sei admin allora puoi fare tutto, se invece sei registrato controllo se sei quel cuoco
                           altrimenti pagina di errore con il messaggio -> non hai il permesso 
                        .requestMatchers(HttpMethod.GET, "/cuoco/**").hasAnyAuthority("ADMIN","REGISTRATO")
                        .requestMatchers(HttpMethod.POST, "/cuoco/**").hasAnyAuthority("ADMIN","REGISTRATO")
                        */
                        
                        // solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority("ADMIN", "REGISTRATO")
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("ADMIN", "REGISTRATO")
                        // tutti gli utenti autenticati possono accere alle pag
                        .anyRequest().authenticated())
                       
                
                // LOGIN: qui definiamo come è gestita l'autenticazione
				// usiamo il protocollo formlogin       
                .formLogin(formLogin -> formLogin
                         // la pagina di login si trova a /login
                        .loginPage("/login")
                        .permitAll()
                        // se il login ha successo, si viene rediretti al path /default
                        .defaultSuccessUrl("/success", true)
                        .failureUrl("/login?error=true"))
                
                // LOGOUT: qui definiamo il logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .clearAuthentication(true).permitAll());

        
        return http.build();
    }
}
