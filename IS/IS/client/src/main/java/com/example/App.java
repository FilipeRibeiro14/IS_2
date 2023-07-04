package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//import com.example.DoubleStatistics;

public class App {

    static WebClient client = getWebClient();
    static String url = "http://localhost:8080/student/all";


    public static void main(String[] args) throws Exception {

        //System.out.println("\n\n-----------------INICIO-------------\n\n");

        writeToFile("Nomes e datas de nascimento:\n");
        getNamesAndBirthdates(url).subscribe(App::writeToFile);
        //getNamesAndBirthdates("http://localhost:8080/student/all").subscribe(System.out::println);
        Thread.sleep(3000);

        writeToFile("\nNumero de estudantes: ");
        getStudentNumber(url).subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\n\nNumero de estudantes ativos: ");
        getActiveStudents(url).subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\n\nNumero de courses acabados: ");
        getCoursesCompleted(url).subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\n\nDados dos estudantes no ultimo ano:\n");
        getStudentsLastYear(url).subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\nMedia de todos os estudantes: ");
        getAvgGrade(url).subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\n\nDesvio Padrao de todos os estudantes: ");
        getStdDev(url).subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\n\nMedia de todos os estudantes graduados: ");
        getAvgGradeFinished(url).subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\n\nDesvio Padrao de todos os estudantes graduados: ");
        getStdDevFinished(url).subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\n\nEstudante mais velho: ");
        getEldest(url).subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\n\nNumero de professores por estudante: ");
        getAvgProfessorsPerStudent("http://localhost:8080/relacionamento/all").subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\n\nNomes de estudantes para cada professor:\n");
        getNamesPerProfessor().subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\nNumero de estudantes para cada professor: \n");
        getNumberOfStudentsPerProfessor().subscribe(App::writeToFile);
        Thread.sleep(3000);

        writeToFile("\nInformacao completa dos estudantes, com professores: \n");
        getFullStudentData().subscribe(App::writeToFile);
        Thread.sleep(3000);

    }

    public static Flux<String> getNamesAndBirthdates(String url) {
        return client
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Student.class)
                .retry(3)
                .map(st -> st.getName() + ", " + st.getBirth() + "\n");
    }

    public static Flux<String> getStudentsLastYear(String url) {
        return client
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Student.class)
            .filter(st -> st.getCredits() > 119 && st.getAvg_grade() < 180) 
            .map(st -> st.getName() + ", " + st.getBirth() + ", " + st.getCredits() + " credits, " + st.getAvg_grade() + " GPA\n");
    } 

    public static Mono<Long> getStudentNumber(String url) {
        return client
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Student.class)
                .count();
    }

    public static Mono<Integer> getCoursesCompleted(String url) {
        return client
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Student.class)
            .map(st -> (Integer) (st.getCredits() / 6))
            .reduce(0, (total, next) -> total + next);
    } 

    public static Mono<Long> getActiveStudents(String url) {
        return client
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Student.class)
                .filter(st -> st.getAvg_grade() < 180)
                .count();
    }

    public static Mono<Double> getAvgGrade(String url) {
        return client
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Student.class)
            .collect(Collectors.averagingDouble(st -> st.getAvg_grade()));
    }

    public static Mono<Object> getStdDev(String url) {
        return client
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Student.class)
            .collect(Collectors.mapping(st -> (double) st.getAvg_grade(), Collector.of(
                DoubleStatistics::new,
                DoubleStatistics::accept,
                DoubleStatistics::combine,
                stdev -> stdev.getStandardDeviation()
                )
            )
            );
    }

    public static Mono<Double> getAvgGradeFinished(String url) {
        return client
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Student.class)
            .filter(st -> st.getCredits() == 180)
            .collect(Collectors.averagingDouble(st -> st.getAvg_grade()));
    }

    public static Mono<Object> getStdDevFinished(String url) {
        return client
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Student.class)
            .filter(st -> st.getCredits() == 180)
            .collect(Collectors.mapping(st -> (double) st.getAvg_grade(), Collector.of(
                DoubleStatistics::new,
                DoubleStatistics::accept,
                DoubleStatistics::combine,
                stdev -> stdev.getStandardDeviation()
                )
            )
            );
    }

    public static Flux<String> getEldest(String url) {
        return client
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Student.class)
            .sort(Comparator.comparing(st -> st.getBirth()))
            .take(1)
            .map(st -> st.getName());
    }

    public static Mono<Float> getAvgProfessorsPerStudent(String url) {
        Mono<Long> nRel = client.get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Relacionamento.class)
            .count();

        Mono<Long> nStudents = client.get()
                .uri("http://localhost:8080/student/all")
                .retrieve()
                .bodyToFlux(Student.class)
                .count();

        return Mono.zip(nRel, nStudents, (a, b) -> (float) a/b);
            
    }

    public static Flux<Object> getNamesPerProfessor() {
        return client.get()
            .uri("http://localhost:8080/professor/all")
            .retrieve()
            .bodyToFlux(Professor.class)
            .flatMap(p -> client.get()
                            .uri("http://localhost:8080/relacionamento/all")
                            .retrieve()
                            .bodyToFlux(Relacionamento.class)
                            .filter(r -> r.getProfessor_id() == p.getId())
                            .flatMap(r -> client.get()
                                        .uri("http://localhost:8080/student/all")
                                        .retrieve()
                                        .bodyToFlux(Student.class)
                                        .filter(s -> s.getId() == r.getStudent_id())
                                        .map(s -> "Professor: " + p.getName() + " Estudante: " + s.getName() + "\n")));
    }

    public static Flux<Object> getNumberOfStudentsPerProfessor() {
        return client.get()
                .uri("http://localhost:8080/professor/all")
                .retrieve()
                .bodyToFlux(Professor.class)
                .flatMap(p -> client.get()
                                .uri("http://localhost:8080/relacionamento/all")
                                .retrieve()
                                .bodyToFlux(Relacionamento.class)
                                .filter(r -> r.getProfessor_id() == p.getId())
                                .count()
                                .map(a -> "Professor " + p.getName() + " tem " + String.valueOf(a) + " alunos.\n"));
    }

    public static Flux<Object> getFullStudentData() {
        return client.get()
            .uri("http://localhost:8080/student/all")
            .retrieve()
            .bodyToFlux(Student.class)
            .flatMap(p -> client.get()
                            .uri("http://localhost:8080/relacionamento/all")
                            .retrieve()
                            .bodyToFlux(Relacionamento.class)
                            .filter(r -> r.getStudent_id() == p.getId())
                            .flatMap(r -> client.get()
                                        .uri("http://localhost:8080/professor/all")
                                        .retrieve()
                                        .bodyToFlux(Professor.class)
                                        .filter(s -> s.getId() == r.getProfessor_id())
                                        .map(s -> " Professor: " + s.getName()))
                            .switchIfEmpty(Mono.just(" Não tem professores"))
                            .map(s -> "Name: " + p.getName() + " Birth: " + p.getBirth() + " Credits: " + p.getCredits() + " GPA: " + p.getAvg_grade() + s + "\n"));
                                        
    }

    public static WebClient getWebClient() {
        WebClient.Builder webClientBuilder = WebClient.builder();
        return webClientBuilder.build();
    }

//----------------------------------------------------------------------

    public static void writeToFile(Object obj) {

          try {
            FileWriter myWriter = new FileWriter("output.txt", true);
            myWriter.write(obj + "");
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }


}