'use strict';

describe('Controller Tests', function() {

    describe('ExtensionStatistics Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockExtensionStatistics;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockExtensionStatistics = jasmine.createSpy('MockExtensionStatistics');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ExtensionStatistics': MockExtensionStatistics
            };
            createController = function() {
                $injector.get('$controller')("ExtensionStatisticsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hireApp:extensionStatisticsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
