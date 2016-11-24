'use strict';

describe('Controller Tests', function() {

    describe('CareerPortalQuestionnaireQuestion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCareerPortalQuestionnaireQuestion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCareerPortalQuestionnaireQuestion = jasmine.createSpy('MockCareerPortalQuestionnaireQuestion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CareerPortalQuestionnaireQuestion': MockCareerPortalQuestionnaireQuestion
            };
            createController = function() {
                $injector.get('$controller')("CareerPortalQuestionnaireQuestionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hireApp:careerPortalQuestionnaireQuestionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
