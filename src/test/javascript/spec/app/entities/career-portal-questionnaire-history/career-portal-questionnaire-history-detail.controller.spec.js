'use strict';

describe('Controller Tests', function() {

    describe('CareerPortalQuestionnaireHistory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCareerPortalQuestionnaireHistory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCareerPortalQuestionnaireHistory = jasmine.createSpy('MockCareerPortalQuestionnaireHistory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CareerPortalQuestionnaireHistory': MockCareerPortalQuestionnaireHistory
            };
            createController = function() {
                $injector.get('$controller')("CareerPortalQuestionnaireHistoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hireApp:careerPortalQuestionnaireHistoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
