//package com.example.dataquery.services
//
//import com.example.dataquery.models.SearchCriteria
//import spock.lang.Specification
//import spock.lang.Subject
//import spock.lang.Unroll
//
//import java.time.LocalDate
//
//class FilterServiceTest extends Specification {
//
//    FilterService
//
//    @Subject
//    FilterService filterService
//
//    void setup() {
//        filterService = new FilterService()
//    }
//
//    @Unroll
//    def 'test buildParams'() {
//        given:
//
//        when:
//        List<SearchCriteria> extractedParameters = filterService.buildParams(queryString)
//
//        then:
//        0 * _
//        noExceptionThrown()
//        extractedParameters == expectedParameters
//
//        where:
//        queryString       || expectedParameters || dateOfBirth
//        'EQUAL(id, "second-post")'        || null     || LocalDate.of(1990, 12, 10)
//        'EQUAL(id,"second-post"),LESS_THAN(views,50)'          || ''       || LocalDate.of(1990, 12, 10)
//        'EQUAL(id,"first-post"),EQUAL(views,1)'        || ''       || LocalDate.of(1990, 12, 10)
//        ''          || null     || LocalDate.of(1990, 12, 10)
//        'Gus'       || null     || LocalDate.of(1990, 12, 10)
//        null        || 'Fring'  || LocalDate.of(1990, 12, 10)
//        ''          || 'Fring'  || LocalDate.of(1990, 12, 10)
//        'Gus'       || ''       || LocalDate.of(1990, 12, 10)
//        'Gus Fring' || ''       || LocalDate.of(1990, 12, 10)
//        'Gus'       || 'Fring'  || LocalDate.of(1990, 12, 10)
//        'Gus'       || 'Fring'  || null
//    }
//
//    def "SearchPosts"() {
//    }
//}
